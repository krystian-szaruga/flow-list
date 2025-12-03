package com.olr261dn.project.details.utils

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.StatsViewModel
import com.olr261dn.viewmodel.TaskItemViewModel
import java.util.UUID

@Composable
internal fun TasksCard(
    tasks: List<Task>,
    theme: ThemeColor,
    context: Context,
    statsViewModel: StatsViewModel,
    notificationViewModel: ReminderViewModel,
    taskViewModel: TaskItemViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.tertiary,
            contentColor = theme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TasksHeader(
                taskCount = tasks.size,
                theme = theme
            )

            if (tasks.isEmpty()) {
                EmptyTasksMessage(theme = theme)
            } else {
                TasksList(
                    tasks = tasks,
                    isExpanded = isExpanded,
                    theme = theme,
                    context = context,
                    statsViewModel = statsViewModel,
                    taskViewModel = taskViewModel,
                    notificationViewModel = notificationViewModel,
                    onToggleExpanded = { isExpanded = !isExpanded }
                )
            }
        }
    }
}

@Composable
private fun TasksHeader(
    taskCount: Int,
    theme: ThemeColor
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = theme.onTertiary
            )
            Text(
                text = stringResource(R.string.tasks, taskCount),
                fontSize = 20.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onTertiary
            )
        }
    }
}

@Composable
private fun EmptyTasksMessage(theme: ThemeColor) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_tasks),
            fontSize = 15.nonScaledSp,
            fontStyle = FontStyle.Italic,
            color = theme.onTertiary.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun TasksList(
    tasks: List<Task>,
    isExpanded: Boolean,
    theme: ThemeColor,
    context: Context,
    statsViewModel: StatsViewModel,
    taskViewModel: TaskItemViewModel,
    notificationViewModel: ReminderViewModel,
    onToggleExpanded: () -> Unit
) {
    val displayedTasks = if (isExpanded) tasks else tasks.take(2)

    val onPress: (Task, Boolean) -> Unit = { task, isCompleted ->
        handleTaskCompletion(
            task = task,
            taskViewModel = taskViewModel,
            notificationViewModel = notificationViewModel,
            statsViewModel = statsViewModel,
            isCompleted = isCompleted
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        displayedTasks.forEach { task ->
            TaskItemCard(
                task = task,
                theme = theme,
                context = context,
                statsViewModel = statsViewModel,
                onMarkDone = { onPress(task, true) },
                onMarkUndone = { onPress(task, false) }
            )
        }

        if (tasks.size > 2) {
            TasksExpandControl(
                tasks = tasks,
                isExpanded = isExpanded,
                theme = theme,
                onToggle = onToggleExpanded
            )
        }
    }
}


@Composable
private fun TasksExpandControl(
    tasks: List<Task>,
    isExpanded: Boolean,
    theme: ThemeColor,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isExpanded) {
            Text(
                text = "+${tasks.size - 2} more tasks",
                fontSize = 13.nonScaledSp,
                color = theme.onTertiary.copy(alpha = 0.7f),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ExpandToggleButton(
            isExpanded = isExpanded,
            theme = theme,
            onClick = onToggle
        )
    }
}

@Composable
private fun ExpandToggleButton(
    isExpanded: Boolean,
    theme: ThemeColor,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(32.dp)
    ) {
        Icon(
            imageVector = if (isExpanded) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            },
            contentDescription = if (isExpanded) "Show less" else "Show all",
            tint = theme.onTertiary,
            modifier = Modifier.size(28.dp)
        )
    }
}

private fun handleTaskCompletion(
    task: Task,
    taskViewModel: TaskItemViewModel,
    notificationViewModel: ReminderViewModel,
    statsViewModel: StatsViewModel,
    isCompleted: Boolean
) {
    val now = System.currentTimeMillis()

    statsViewModel.add(
        CompletionHistory(
            id = UUID.randomUUID(),
            taskId = task.id,
            projectId = task.goalId,
            date = now,
            isCompleted = isCompleted,
            markedAt = now
        )
    )

    taskViewModel.updateItem(task.copy(isDisabled = true))

    if (task.scheduledTime != 0L) {
        notificationViewModel.cancelNotification(ReminderType.TASK, task.id)
    }
}