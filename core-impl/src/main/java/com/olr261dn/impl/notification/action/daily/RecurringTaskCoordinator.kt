package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.notification.NotificationManagement
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.impl.notification.action.ItemCoordinator
import com.olr261dn.impl.notification.action.utils.DelayableCoordinator
import kotlinx.coroutines.flow.first

internal class RecurringTaskCoordinator (
    private val delayPreferencesActions: DelayPreferencesActions,
    action: RecurringTaskActions,
    reminderActions: ReminderActions? = null,
    notificationManager: NotificationManagement,
    rescheduler: Rescheduler,
    nextScheduleCalculator: NextScheduleCalculator,
    completionHistory: CompletionHistoryActions? = null,
    isCompleted: Boolean = true
): ItemCoordinator<RecurringTask>(
    action,
    reminderActions,
    notificationManager,
    nextScheduleCalculator,
    rescheduler,
    completionHistory,
    isCompleted
),
    DelayableCoordinator<RecurringTask>
{
    override fun mapToItemIds(item: RecurringTask): ItemIds = ItemIds(dailyTaskId = item.id)
    override suspend fun getDelayValue(): Long =
        delayPreferencesActions.getRecurringTaskDelay.execute().first()

}