package com.olr261dn.impl.notification.action.goal

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.notification.NotificationManagement
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.impl.notification.action.ItemCoordinator

internal class ProjectCoordinator (
    action: ProjectActions,
    reminderActions: ReminderActions?,
    notificationManager: NotificationManagement,
    nextScheduleCalculator: NextScheduleCalculator,
    rescheduler: Rescheduler,
    completionHistory: CompletionHistoryActions?,
    isCompleted: Boolean = true
): ItemCoordinator<Project>(
    action, reminderActions, notificationManager, nextScheduleCalculator,
    rescheduler, completionHistory, isCompleted
) {
    override fun mapToItemIds(item: Project): ItemIds = ItemIds(goalId = item.id)
}