package com.olr261dn.task

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.Task
import com.olr261dn.ui.compose.base.BaseDetailScreenView
import com.olr261dn.ui.compose.base.ReminderConfig
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.TaskItemViewModel
import java.util.UUID

internal object TaskDetail: BaseDetailScreenView<Task, TaskItemViewModel, ReminderViewModel>() {
    override val config: ReminderConfig<Task> = TaskReminderConfig
    private const val TAG = "TaskDetailView"

    @Composable
    override fun getNotificationViewModel(): ReminderViewModel = hiltViewModel<ReminderViewModel>()

    @Composable
    override fun getItem(id: UUID): Task =
        getViewModel().items.collectAsState().value.firstOrNull { it.id == id }
            ?: Task(id= UUID.randomUUID())

    @Composable
    override fun getViewModel(): TaskItemViewModel = hiltViewModel()

    @Composable
    override fun DetailsBody(item: Task) {
        val viewModel = getViewModel()
        val notificationViewModel = hiltViewModel<ReminderViewModel>()
        DetailsContent(item) {
            val updatedItem = item.copy(isDisabled = !item.isDisabled)
            viewModel.updateItem(updatedItem)
            if (updatedItem.isDisabled) {
                notificationViewModel.cancelNotification(config.getType(), updatedItem.id)
            } else {
                if (updatedItem.scheduledTime > System.currentTimeMillis()) {
                    notificationViewModel.setNotification(
                        config.getRepeatPattern(updatedItem),
                        config.getType(),
                        updatedItem.scheduledTime,
                        updatedItem.id
                    )
                } else {
                    Log.i(
                        TAG,
                        "DetailsBody: Notification Will Not Be Set As The Scheduled Time is In Past"
                    )
                }
            }
        }
    }
    @Composable
    override fun Popup(
        item: Task,
        onDismiss: () -> Unit,
        onEdit: (Task) -> Unit,
    ) {
        AddTask(
            id = item.id,
            title = item.title,
            goalId = item.goalId,
            description = item.description,
            scheduledDateTime = item.scheduledTime
        ).Show(onDismiss = { onDismiss() }, onAdd = { onEdit(it) })
    }
}
