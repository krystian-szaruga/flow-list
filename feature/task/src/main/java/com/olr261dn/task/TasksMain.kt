package com.olr261dn.task

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Task
import com.olr261dn.ui.compose.base.BaseScreenView
import com.olr261dn.ui.compose.base.ReminderConfig
import com.olr261dn.ui.compose.common.TaskDelayedCard
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.TaskItemViewModel
import com.olr261dn.viewmodel.TaskNotificationActionsViewModel

internal object TasksMain: BaseScreenView<Task, TaskItemViewModel, ReminderViewModel>() {
    @Composable
    override fun getViewModel(): TaskItemViewModel = hiltViewModel<TaskItemViewModel>()

    @Composable
    override fun getReminderViewModel(): ReminderViewModel =
        hiltViewModel<ReminderViewModel>()

    @Composable
    override fun GetAddPopup(onDismiss: () -> Unit) {
        val viewModel = getViewModel()
        val notificationViewModel = hiltViewModel<ReminderViewModel>()
        AddTask().Show(
            onDismiss = { onDismiss() },
            onAdd= { onAdd(it, viewModel, notificationViewModel) })
    }

    @Composable
    override fun ItemDisplayer(
        item: Task,
        isSelected: Boolean,
        onClick: (Task) -> Unit,
        onLongClick: (Task) -> Unit,
    ) {
        val viewModel = getViewModel()
        val reminderViewModel = getReminderViewModel()
        val context = LocalContext.current
        val theme = customTheme()
        val formattedDate = remember(item.scheduledTime) {
            formatDate(
                item.scheduledTime,
                context
            )
        }
        val textStyle = MaterialTheme.typography.titleMedium.copy(
            textDecoration = if (
                item.isDisabled
            ) {
                TextDecoration.LineThrough
            } else {
                null
            }
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable(
                    onClick = { onClick(item) },
                    onLongClick = { onLongClick(item) }
                ),
            color = customTheme().getSurface(isSelected),
            contentColor = customTheme().getOnSurface(isSelected),
            tonalElevation = if (isSelected) 4.dp else 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(theme.getCompletedColor(!item.isDisabled))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.title,
                        fontSize = 40.nonScaledSp,
                        style = textStyle
                    )

                    if (item.description.isNotEmpty()) {
                        Text(
                            text = item.description,
                            maxLines = 1,
                            style = textStyle,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.nonScaledSp
                        )
                    }
                    Text(
                        text = formattedDate,
                        style = textStyle,
                        fontSize = 15.nonScaledSp
                    )
                    if (item.delayedTime != null) {
                        val actionViewModel = hiltViewModel<TaskNotificationActionsViewModel>()
                        TaskDelayedCard(
                            type = ReminderType.TASK,
                            item = item,
                            isSelected = isSelected,
                            reminderViewModel = reminderViewModel,
                            actionViewModel = actionViewModel
                        ) {
                            viewModel.cancelDelay(item.copy(delayedTime = null))
                        }
                    }
                }
            }
        }
    }
    override val config: ReminderConfig<Task> = TaskReminderConfig
}