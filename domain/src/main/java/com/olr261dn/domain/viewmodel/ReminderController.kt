package com.olr261dn.domain.viewmodel

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.RepeatPattern
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface ReminderController {

    val reminders: StateFlow<List<Reminder>>

    fun setNotificationsForGoal(item: GoalWithTasks)

    fun setNotification(
        repeatPattern: RepeatPattern,
        type: ReminderType,
        scheduledTime: Long,
        itemId: UUID
    )

    fun cancelNotification(
        type: ReminderType,
        itemId: UUID
    )
}
