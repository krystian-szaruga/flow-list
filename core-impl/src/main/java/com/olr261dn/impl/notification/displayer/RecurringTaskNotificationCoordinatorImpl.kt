package com.olr261dn.impl.notification.displayer

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.RecurringTaskNotificationCoordinator
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.domain.usecase.RecurringTaskActions
import java.util.UUID
import javax.inject.Inject

internal class RecurringTaskNotificationCoordinatorImpl @Inject constructor(
    itemAction: RecurringTaskActions,
    notification: DailyTaskNotification,
): BaseNotificationCoordinator<RecurringTask>(itemAction, notification),
    RecurringTaskNotificationCoordinator {
    override fun getActions(id: Long, item: RecurringTask): List<NotificationAction> {
        return listOf(
            NotificationAction(id, ActionType.DONE, ReminderType.DAILY, item.id),
            NotificationAction(id, ActionType.DELAY, ReminderType.DAILY, item.id),
            NotificationAction(id, ActionType.SKIP, ReminderType.DAILY, item.id),
        )
    }

    override fun dismissAction(id: Long, itemId: UUID): NotificationAction =
        NotificationAction(id, ActionType.SKIP, ReminderType.DAILY, itemId)

}