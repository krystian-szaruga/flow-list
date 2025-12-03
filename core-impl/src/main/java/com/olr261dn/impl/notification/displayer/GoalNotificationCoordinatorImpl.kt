package com.olr261dn.impl.notification.displayer

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.domain.notification.GoalNotification
import com.olr261dn.domain.notification.GoalNotificationCoordinator
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.domain.usecase.ProjectActions
import java.util.UUID
import javax.inject.Inject

internal class GoalNotificationCoordinatorImpl @Inject constructor(
    itemAction: ProjectActions,
    notification: GoalNotification,
): BaseNotificationCoordinator<Project>(itemAction, notification), GoalNotificationCoordinator {
    override fun getActions(id: Long, item: Project): List<NotificationAction> {
        return listOfNotNull(
            NotificationAction(id, ActionType.DONE, ReminderType.GOAL, item.id),
            NotificationAction(id, ActionType.SKIP, ReminderType.GOAL, item.id),
        )
    }

    override fun dismissAction(id: Long, itemId: UUID): NotificationAction =
        NotificationAction(id, ActionType.SKIP, ReminderType.GOAL, itemId)
}