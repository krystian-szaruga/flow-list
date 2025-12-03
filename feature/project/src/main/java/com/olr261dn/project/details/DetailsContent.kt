package com.olr261dn.project.details

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.project.details.utils.GoalDetailCard
import com.olr261dn.project.details.utils.TasksCard
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.GoalItemViewModel
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.StatsViewModel
import com.olr261dn.viewmodel.TaskItemViewModel
import java.util.UUID


@Composable
fun DetailsContent(
    item: GoalWithTasks,
    notificationViewModel: ReminderViewModel,
    taskViewModel: TaskItemViewModel,
    goalViewModel: GoalItemViewModel
) {
    val theme = customTheme()
    val context = LocalContext.current
    val statsViewModel = hiltViewModel<StatsViewModel>()
    val lastStatus by statsViewModel.goalLastStatus(item.project.id).collectAsState(initial = null)
    val goalIsCompleted = lastStatus?.isCompleted
    val onPress: (Boolean) -> Unit = {
        onGoalMarkButton(item, goalViewModel, statsViewModel, notificationViewModel, it)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.surface)
    ) {
        DetailsHeader(
            isDisabled = item.project.isDisabled,
            theme = theme,
            onMarkGoalDone = { onPress(true) },
            onMarkGoalUndone = { onPress(false) }
        )

        DetailsScrollableContent(
            item = item,
            theme = theme,
            context = context,
            isCompleted = goalIsCompleted,
            statsViewModel = statsViewModel,
            taskViewModel = taskViewModel,
            notificationViewModel = notificationViewModel
        )
    }
}

@Composable
private fun DetailsHeader(
    isDisabled : Boolean,
    theme: ThemeColor,
    onMarkGoalDone: () -> Unit,
    onMarkGoalUndone: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = theme.surface,
        contentColor = theme.onSurface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.goal_details),
                fontSize = 20.nonScaledSp,
                fontWeight = FontWeight.Bold
            )
            if (!isDisabled) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onMarkGoalDone,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = stringResource(R.string.mark_as_done),
                            tint = theme.greenOnSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = onMarkGoalUndone,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.mark_as_skip),
                            tint = theme.redOnSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun onGoalMarkButton(
    goalWithTasks: GoalWithTasks,
    goalViewModel: GoalItemViewModel,
    statsViewModel: StatsViewModel,
    notificationViewModel: ReminderViewModel,
    isCompleted: Boolean
) {
    val now = System.currentTimeMillis()
    statsViewModel.add(
        CompletionHistory(
            id = UUID.randomUUID(),
            projectId = goalWithTasks.project.id,
            date = now,
            isCompleted = isCompleted,
            markedAt = now
        )
    )
    goalViewModel.updateGoalWithTasks(
        goalWithTasks.copy(
            project = goalWithTasks.project.copy(isDisabled = true),
            tasks = goalWithTasks.tasks.map { it.copy(isDisabled = true) }
        )
    )
    notificationViewModel.cancelNotification(ReminderType.GOAL, goalWithTasks.project.id)
    goalWithTasks.tasks.forEach { task ->
        if (task.scheduledTime != 0L) {
            notificationViewModel.cancelNotification(ReminderType.TASK, task.id)
        }
    }
}



@Composable
private fun DetailsScrollableContent(
    item: GoalWithTasks,
    theme: ThemeColor,
    context: Context,
    isCompleted: Boolean?,
    statsViewModel: StatsViewModel,
    taskViewModel: TaskItemViewModel,
    notificationViewModel: ReminderViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GoalDetailCard(
            project = item.project,
            theme = theme,
            context = context,
            isCompleted = isCompleted
        )
        TasksCard(
            tasks = item.tasks,
            theme = theme,
            context = context,
            statsViewModel = statsViewModel,
            notificationViewModel = notificationViewModel,
            taskViewModel = taskViewModel
        )
    }
}
