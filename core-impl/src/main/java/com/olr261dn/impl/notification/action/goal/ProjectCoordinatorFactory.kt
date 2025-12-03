package com.olr261dn.impl.notification.action.goal

import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.ReminderActions
import javax.inject.Inject

internal class ProjectCoordinatorFactory @Inject constructor(
    private val action: ProjectActions,
    private val reminderActions: ReminderActions,
    private val displayer: DailyTaskNotification,
    private val nextScheduleCalculator: NextScheduleCalculator,
    private val rescheduler: Rescheduler,
    private val completionHistory: CompletionHistoryActions,
) {
    fun create(isCompleted: Boolean) = ProjectCoordinator(
        action = action,
        reminderActions = reminderActions,
        notificationManager = displayer,
        nextScheduleCalculator = nextScheduleCalculator,
        rescheduler = rescheduler,
        completionHistory = completionHistory,
        isCompleted = isCompleted
    )
}