package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.ReminderActions
import javax.inject.Inject

internal class RecurringTaskCoordinatorFactory @Inject constructor(
    private val delayPreferencesActions: DelayPreferencesActions,
    private val action: RecurringTaskActions,
    private val nextScheduleCalculator: NextScheduleCalculator,
    private val reminderActions: ReminderActions,
    private val displayer: DailyTaskNotification,
    private val rescheduler: Rescheduler,
    private val completionHistory: CompletionHistoryActions,
) {
    fun create(isCompleted: Boolean) = RecurringTaskCoordinator(
        delayPreferencesActions = delayPreferencesActions,
        action = action,
        reminderActions = reminderActions,
        notificationManager = displayer,
        rescheduler = rescheduler,
        completionHistory = completionHistory,
        nextScheduleCalculator = nextScheduleCalculator,
        isCompleted = isCompleted
    )

    fun createSimple() = RecurringTaskCoordinator(
        delayPreferencesActions = delayPreferencesActions,
        action = action,
        reminderActions = null,
        notificationManager = displayer,
        rescheduler = rescheduler,
        completionHistory = null,
        nextScheduleCalculator = nextScheduleCalculator,
        isCompleted = false
    )
}