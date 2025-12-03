package com.olr261dn.project

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.Task
import com.olr261dn.project.utils.AddTaskPopup
import com.olr261dn.project.utils.DisplayLinkedTasks
import com.olr261dn.project.utils.EditTaskPopup
import com.olr261dn.project.utils.LinkTaskPopup
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.popup.AddItemPopup
import java.util.UUID

internal class AddProject(
    @field:StringRes private val confirmLabel: Int = R.string.save,
    private val id: UUID? = null,
    private val title: String = "",
    private val description: String = "",
    private val scheduledDateTime: Long = 0L,
    private val listOfTasks: List<Task> = emptyList(),
    private val createdAt: Long = System.currentTimeMillis()
) {
    private enum class PopupType { LINK, ADD, NONE }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Show(onAdd: (GoalWithTasks) -> Unit, onDismiss: () -> Unit) {
        val tasks = rememberSaveable {
            mutableStateListOf<Task>().apply { addAll(listOfTasks) }
        }
        var popupType by rememberSaveable { mutableStateOf(PopupType.NONE) }
        var taskToModify by rememberSaveable { mutableStateOf<Task?>(null) }
        var goalScheduledTime by rememberSaveable { mutableLongStateOf(scheduledDateTime) }
        val earliestTaskTime by remember {
            derivedStateOf {
                tasks
                    .filter { it.scheduledTime > 0L }
                    .minOfOrNull { it.scheduledTime }
            }
        }
        val addTask: (Task) -> Unit = { tasks.add(it.copy(id = UUID.randomUUID())) }

        key("goal_popup") {
            AddItemPopup(
                confirmLabel = confirmLabel,
                titleLabel = R.string.goal_title,
                id = this.id,
                title = this.title,
                description = this.description,
                scheduledDateTime = this.scheduledDateTime,
                onDismiss = onDismiss,
                minScheduledTime = earliestTaskTime,
                onScheduledTimeChanged = { time ->
                    goalScheduledTime = time
                },
                additionalContent = {
                    DisplayLinkedTasks(
                        tasks,
                        onModifyPress = {
                            taskToModify = it
                            popupType = PopupType.ADD
                        },
                        onDeleteTask = { tasks.remove(it) },
                        onAddPress = { popupType = PopupType.ADD },
                        onLinkPress = { popupType = PopupType.LINK }
                    )
                }
            ) { id, title, description, time, _ ->
                addGoal(id, title, description, time) {
                    onAdd(GoalWithTasks(it, tasks))
                }
            }
        }

        HandlePopupType(
            popupType = popupType,
            taskToModify = taskToModify,
            goalScheduledTime = goalScheduledTime,
            tasks = tasks,
            addTask = addTask,
            onDismissPopup = {
                popupType = PopupType.NONE
                taskToModify = null
            }
        )
    }

    private fun addGoal(
        id: UUID?,
        title: String,
        description: String,
        time: Long,
        onAdd: (Project) -> Unit
    ) {
        onAdd(
            Project(
                id = id ?: UUID.randomUUID(),
                title = title,
                scheduledTime = time,
                delayedTime = null,
                description = description,
                createdAt = this.createdAt,
                isDisabled = false
            )
        )
    }

    @Composable
    private fun HandlePopupType(
        popupType: PopupType,
        taskToModify: Task?,
        goalScheduledTime: Long,
        tasks: MutableList<Task>,
        addTask: (Task) -> Unit,
        onDismissPopup: () -> Unit
    ) {
        when (popupType) {
            PopupType.ADD -> {
                key("nested_task_${taskToModify?.id ?: "new"}") {
                    if (taskToModify == null) {
                        AddTaskPopup(
                            maxScheduledTime = if (goalScheduledTime > 0L) goalScheduledTime else null,
                            onDismiss = onDismissPopup
                        ) { task ->
                            addTask(task)
                            onDismissPopup()
                        }
                    } else {
                        EditTaskPopup(
                            task = taskToModify,
                            maxScheduledTime = if (goalScheduledTime > 0L) goalScheduledTime else null,
                            onDismiss = onDismissPopup
                        ) { updatedTask ->
                            tasks.replaceAll {
                                if (it.id == updatedTask.id) updatedTask else it
                            }
                            onDismissPopup()
                        }
                    }
                }
            }
            PopupType.LINK -> {
                key("link_task_popup") {
                    LinkTaskPopup(
                        titles = tasks.map { it.title }.distinct().toSet(),
                        onDismiss = onDismissPopup,
                        onAdd = {
                            addTask(it.copy(scheduledTime = 0L))
                            onDismissPopup()
                        }
                    )
                }
            }
            PopupType.NONE -> { }
        }
    }
}
