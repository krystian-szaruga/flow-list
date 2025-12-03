package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.Task
import com.olr261dn.impl.notification.action.ReschedulingCoordinator

internal abstract class ReschedulingTask(
     coordinator: TaskCoordinator
): ReschedulingCoordinator<Task>(coordinator) {
    private val taskCoordinator: TaskCoordinator
        get() = super.coordinator as TaskCoordinator

    override suspend fun completeAndReschedule(item: Task, reminder: Reminder): Boolean {
        val newTime = coordinator.getNewTime(item.scheduledTime, reminder.repeatPattern)
        if (newTime == null || isAfterProjectTime(newTime, getProjectTime(item))) {
            taskCoordinator.updateItem(item.copy(
                isDisabled = true,
                delayedTime = null
            ))
            taskCoordinator.addCompletionHistoryRecord(item)
        } else {
            taskCoordinator.updateItem(item.copy(scheduledTime = newTime, delayedTime = null))
            taskCoordinator.addCompletionHistoryRecord(item)
            taskCoordinator.rescheduleNotification(
                notificationId = reminder.id,
                itemID = item.id,
                newTime = newTime,
                reminderType = reminder.type
            )
        }
        return true
    }

    private fun isAfterProjectTime(newTime: Long, projectTime: Long): Boolean = newTime > projectTime

    private suspend fun getProjectTime(item: Task): Long =
        taskCoordinator.getLinkedProject(item.goalId)?.scheduledTime ?: 0L
}