package com.olr261dn.impl.notification.action

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.notification.NotificationManagement
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.BaseItemActions
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.impl.notification.action.utils.logAndRunSuspended
import com.olr261dn.impl.notification.action.utils.millisToReadableDate
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

internal abstract class ItemCoordinator<T: Schedulable>(
    private val action: BaseItemActions<T>,
    private val reminderActions: ReminderActions?,
    private val notificationManager: NotificationManagement,
    private val nextScheduleCalculator: NextScheduleCalculator,
    private val rescheduler: Rescheduler,
    private val completionHistory: CompletionHistoryActions?,
    private val isCompleted: Boolean
) {
    private companion object {
        const val TAG = "ActionCoordinator"
    }

    fun getNewTime(scheduleTime: Long, pattern: RepeatPattern?): Long? =
        nextScheduleCalculator.calculateNext(scheduleTime, pattern)

    fun getAction() = action

    fun dismissNotification(id: Long) {
        notificationManager.cancelNotification(id)
    }

    suspend fun getItem(itemId: UUID): T? =
        logAndRunSuspended(TAG, "Fetched item: ") {
            action.getById.execute(itemId).firstOrNull()
        }

    suspend fun getReminder(id: Long): Reminder? =
        logAndRunSuspended(TAG, "Fetched reminder: ") {
            reminderActions?.getById?.execute(id)?.firstOrNull()
        }

    suspend fun updateItem(newItem: T) {
        logAndRunSuspended(TAG, "Item has been updated with new item: ") {
            action.update.execute(newItem)
            return@logAndRunSuspended "newItem: $newItem," +
                    " scheduledTime: ${newItem.scheduledTime.millisToReadableDate()}"
        }
    }

    suspend fun addCompletionHistoryRecord(item: T) {
        logAndRunSuspended(TAG, "Added CompletionHistory Record: ") {
            if (completionHistory != null) {
                val id = mapToItemIds(item)
                val completionHistoryItem = CompletionHistory(
                    id = UUID.randomUUID(),
                    recurringTaskId = id.dailyTaskId,
                    projectId = id.goalId,
                    taskId = id.taskId,
                    date = System.currentTimeMillis(),
                    isCompleted = isCompleted,
                    markedAt = System.currentTimeMillis()
                )
                completionHistory.add.execute(completionHistoryItem)
                return@logAndRunSuspended completionHistoryItem
            }
            return@logAndRunSuspended null
        }
    }

    protected abstract fun mapToItemIds(item: T): ItemIds


    protected data class ItemIds(
        val dailyTaskId: UUID? = null,
        val taskId: UUID? = null,
        val goalId: UUID? = null
    )

    fun rescheduleNotification(
        notificationId: Long,
        itemID: UUID,
        newTime: Long,
        reminderType: ReminderType
    ) {
        rescheduler.reschedule(
            newTime = newTime,
            notificationId = notificationId,
            itemId = itemID,
            reminderType = reminderType
        )
    }

    fun delayNotification(
        notificationId: Long,
        itemID: UUID,
        reminderType: ReminderType,
        newTime: Long
    ) {
        rescheduler.delayNotification(
            itemId = itemID,
            notificationId = notificationId,
            reminderType = reminderType,
            newTime = newTime
        )
    }
}