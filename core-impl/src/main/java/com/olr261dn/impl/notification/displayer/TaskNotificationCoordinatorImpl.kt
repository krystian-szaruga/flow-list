package com.olr261dn.impl.notification.displayer

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.notification.ActionType
import com.olr261dn.domain.notification.NotificationAction
import com.olr261dn.domain.notification.TaskNotification
import com.olr261dn.domain.notification.TaskNotificationCoordinator
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.TaskActions
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID
import javax.inject.Inject

internal class TaskNotificationCoordinatorImpl @Inject constructor(
    itemAction: TaskActions,
    private val goalAction: ProjectActions,
    notification: TaskNotification,
): BaseNotificationCoordinator<Task>(itemAction, notification), TaskNotificationCoordinator {
    override fun getActions(id: Long, item: Task): List<NotificationAction> {
        return listOfNotNull(
            NotificationAction(id, ActionType.DONE, ReminderType.TASK, item.id),
            NotificationAction(id, ActionType.DELAY, ReminderType.TASK, item.id)
                .takeIf { item.goalId == null },
            NotificationAction(id, ActionType.SKIP, ReminderType.TASK, item.id),
        )
    }

    override fun dismissAction(id: Long, itemId: UUID): NotificationAction =
        NotificationAction(id, ActionType.SKIP, ReminderType.TASK, itemId)

    override suspend fun getSubTitle(item: Task) =
        item.goalId?.let { goalAction.getById.execute(it).firstOrNull()?.title }
}