package com.olr261dn.impl.notification.action.task

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.notification.NotificationManagement
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.DelayPreferencesActions
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.usecase.TaskActions
import com.olr261dn.impl.notification.action.ItemCoordinator
import com.olr261dn.impl.notification.action.utils.DelayableCoordinator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

internal class TaskCoordinator (
    private val delayPreferencesActions: DelayPreferencesActions,
    action: TaskActions,
    reminderActions: ReminderActions?,
    private val projectActions: ProjectActions,
    notificationManager: NotificationManagement,
    nextScheduleCalculator: NextScheduleCalculator,
    rescheduler: Rescheduler,
    completionHistory: CompletionHistoryActions?,
    isCompleted: Boolean = true
): ItemCoordinator<Task>(
    action, reminderActions, notificationManager, nextScheduleCalculator,
    rescheduler, completionHistory, isCompleted
), DelayableCoordinator<Task> {
    override fun mapToItemIds(item: Task): ItemIds = ItemIds(taskId = item.id, goalId = item.goalId)

    suspend fun getLinkedProject(goalId: UUID?): Project? =
        goalId?.let { projectActions.getById.execute(it).firstOrNull() }

    override suspend fun getDelayValue() = delayPreferencesActions.getTaskDelay.execute().first()
}