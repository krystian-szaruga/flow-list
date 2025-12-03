package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.usecase.TaskActions
import javax.inject.Inject

internal class TaskCoordinatorFactory @Inject constructor(
    private val delayPreferencesActions: DelayPreferencesActions,
    private val action: TaskActions,
    private val reminderActions: ReminderActions,
    private val projectActions: ProjectActions,
    private val displayer: DailyTaskNotification,
    private val nextScheduleCalculator: NextScheduleCalculator,
    private val rescheduler: Rescheduler,
    private val completionHistory: CompletionHistoryActions,
) {
    fun create(isCompleted: Boolean) = TaskCoordinator(
        delayPreferencesActions = delayPreferencesActions,
        action = action,
        reminderActions = reminderActions,
        projectActions = projectActions,
        notificationManager = displayer,
        nextScheduleCalculator = nextScheduleCalculator,
        rescheduler = rescheduler,
        completionHistory = completionHistory,
        isCompleted = isCompleted
    )

    fun createSimple() = TaskCoordinator(
        delayPreferencesActions = delayPreferencesActions,
        action = action,
        reminderActions = null,
        notificationManager = displayer,
        projectActions = projectActions,
        nextScheduleCalculator = nextScheduleCalculator,
        rescheduler = rescheduler,
        completionHistory = null,
        isCompleted = false
    )
}