package com.olr261dn.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.project.details.DetailsContent
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.DetailTemplate
import com.olr261dn.viewmodel.GoalItemViewModel
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.TaskItemViewModel
import java.util.UUID

internal object ProjectDetail{
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(id: UUID, onBackPress: () -> Unit) {
        val goalViewModel = hiltViewModel<GoalItemViewModel>()
        val notificationViewModel = hiltViewModel<ReminderViewModel>()
        val item: GoalWithTasks = getItem(id, goalViewModel)
        val taskViewModel = hiltViewModel<TaskItemViewModel>()
        DetailTemplate(
            title = item.project.title,
            onBackPress = onBackPress,
            onRemoveConfirm = {
                onRemoveConfirm(goalViewModel, notificationViewModel, item) {
                    onBackPress()
                }
            },
            popup = { onDismiss ->
                AddProject(
                    confirmLabel = R.string.edit,
                    id = item.project.id,
                    title = item.project.title,
                    description = item.project.description,
                    scheduledDateTime = item.project.scheduledTime,
                    listOfTasks = item.tasks,
                    createdAt = item.project.createdAt
                ).Show(
                    onAdd = {
                        val taskToDelete = item.tasks.filter { originalTask ->
                            it.tasks.none {newTask -> newTask.id == originalTask.id}
                        }
                        taskToDelete.forEach { task ->
                            if (task.scheduledTime != 0L) {
                                notificationViewModel.cancelNotification(ReminderType.TASK, task.id)
                            }
                        }

                        taskViewModel.removeItems(taskToDelete)
                        goalViewModel.updateGoalWithTasks(it)
                        notificationViewModel.setNotificationsForGoal(it)
                    },
                    onDismiss = onDismiss
                )
            }
        ) {
            DetailsContent(
                item = item,
                notificationViewModel = notificationViewModel,
                taskViewModel = taskViewModel,
                goalViewModel = goalViewModel
            )
        }

    }
    @Composable
    private fun getItem(id: UUID, viewModel: GoalItemViewModel): GoalWithTasks =
        viewModel.goalsWithTasks.collectAsState().value.firstOrNull {
            it.project.id == id
        } ?: GoalWithTasks(Project(UUID.randomUUID()), emptyList())

    private fun onRemoveConfirm(
        itemViewModel: GoalItemViewModel,
        notificationViewModel: ReminderViewModel,
        item: GoalWithTasks,
        onBackPress: () -> Unit
    ) {
        val config = ProjectReminderConfig
        itemViewModel.removeItems(listOf(item.project))
        notificationViewModel.cancelNotification(
            type = config.getType(),
            itemId = item.project.id
        )
        item.tasks.forEach {
            notificationViewModel.cancelNotification(
                type = ReminderType.TASK,
                itemId = it.id
            )
        }
        onBackPress()
    }

}