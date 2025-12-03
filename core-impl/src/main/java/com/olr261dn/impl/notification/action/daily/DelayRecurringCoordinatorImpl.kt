package com.olr261dn.impl.notification.action.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.notification.DelayRecurringCoordinator
import com.olr261dn.impl.notification.action.BaseDelayCoordinator
import com.olr261dn.impl.notification.action.utils.DelayableCoordinator

internal class DelayRecurringCoordinatorImpl(
    coordinator: RecurringTaskCoordinator,
): BaseDelayCoordinator<RecurringTask>(coordinator), DelayRecurringCoordinator {
    override val parentCoordinator: DelayableCoordinator<RecurringTask>
        get() = coordinator as RecurringTaskCoordinator

    override fun updateItemWithDelay(item: RecurringTask, delayedTime: Long): RecurringTask =
        item.copy(delayedTime = delayedTime)

    override fun getReminderType(): ReminderType = ReminderType.DAILY
}