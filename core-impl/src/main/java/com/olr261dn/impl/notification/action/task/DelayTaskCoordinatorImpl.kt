package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.notification.DelayTaskCoordinator
import com.olr261dn.impl.notification.action.BaseDelayCoordinator
import com.olr261dn.impl.notification.action.utils.DelayableCoordinator

internal class DelayTaskCoordinatorImpl(
    coordinator: TaskCoordinator,
) : BaseDelayCoordinator<Task>(coordinator), DelayTaskCoordinator {
    override val parentCoordinator: DelayableCoordinator<Task>
        get() = coordinator as TaskCoordinator

    override fun updateItemWithDelay(item: Task, delayedTime: Long): Task =
        item.copy(delayedTime = delayedTime)

    override fun getReminderType(): ReminderType = ReminderType.DAILY
}