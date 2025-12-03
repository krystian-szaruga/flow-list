package com.olr261dn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.viewmodel.ReminderController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val actions: ReminderActions,
    private val scheduler: ReminderScheduler
) : ViewModel(), ReminderController {

    override val reminders: StateFlow<List<Reminder>> = actions.getAll.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override fun setNotificationsForGoal(item: GoalWithTasks) {
        viewModelScope.launch {
            setNotificationSync(
                repeatPattern = RepeatPattern.Once,
                type = ReminderType.GOAL,
                scheduledTime = item.project.scheduledTime,
                itemId = item.project.id
            )
            item.tasks.forEach { task ->
                if (task.scheduledTime != 0L && task.scheduledTime >= System.currentTimeMillis()) {
                    setNotificationSync(
                        repeatPattern = task.repeatPattern,
                        type = ReminderType.TASK,
                        scheduledTime = task.scheduledTime,
                        itemId = task.id
                    )
                }
            }
        }
    }

    override fun setNotification(
        repeatPattern: RepeatPattern,
        type: ReminderType,
        scheduledTime: Long,
        itemId: UUID
    ) {
        viewModelScope.launch {
            setNotificationSync(repeatPattern, type, scheduledTime, itemId)
        }
    }

    private suspend fun setNotificationSync(
        repeatPattern: RepeatPattern,
        type: ReminderType,
        scheduledTime: Long,
        itemId: UUID
    ) {
        val existingReminderId = deleteReminderByItemIdAndType(itemId, type)
        if (existingReminderId != 0L) {
            scheduler.cancelReminder(existingReminderId)
        }

        val id = actions.add.execute(
            Reminder(
                repeatPattern = repeatPattern,
                scheduledTime = scheduledTime,
                type = type,
                itemId = itemId
            )
        )
        scheduler.schedule(scheduledTime, id, type.name, itemId)
    }

    override fun cancelNotification(type: ReminderType, itemId: UUID) {
        viewModelScope.launch {
            val reminderId = deleteReminderByItemIdAndType(itemId, type)
            scheduler.cancelReminder(reminderId)
        }
    }

    private suspend fun deleteReminderByItemIdAndType(itemId: UUID, type: ReminderType): Long {
        val reminder = actions.getByItemIdAndType.execute(itemId, type)
        return reminder?.let {
            actions.deleteByItemIdAndType.execute(itemId, type)
            it.id
        } ?: 0
    }
}