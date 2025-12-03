package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.Reminder
import com.olr261dn.impl.notification.action.ReschedulingCoordinator

internal abstract class ReschedulingDaily(
    coordinator: RecurringTaskCoordinator
): ReschedulingCoordinator<RecurringTask>(coordinator) {
    override suspend fun completeAndReschedule(item: RecurringTask, reminder: Reminder): Boolean {
        return coordinator.getNewTime(item.scheduledTime, reminder.repeatPattern)?.let { newTime ->
            coordinator.updateItem(item.copy(scheduledTime = newTime, delayedTime = null))
            coordinator.addCompletionHistoryRecord(item)
            coordinator.rescheduleNotification(
                notificationId = reminder.id,
                itemID = item.id,
                newTime = newTime,
                reminderType = reminder.type
            )
            true
        } ?: false
    }
}