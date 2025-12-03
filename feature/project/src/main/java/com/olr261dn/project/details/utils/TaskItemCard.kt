package com.olr261dn.project.details.utils

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.StatsViewModel

@Composable
internal fun TaskItemCard(
    task: Task,
    theme: ThemeColor,
    context: Context,
    statsViewModel: StatsViewModel,
    onMarkDone: () -> Unit,
    onMarkUndone: () -> Unit
) {
    val lastStatus by statsViewModel.taskLinkedToGoalLastStatus(task.id, task.goalId)
        .collectAsState(initial = null)
    val isCompleted = lastStatus?.isCompleted

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isDisabled) {
                theme.surface.copy(alpha = 0.6f)
            } else {
                theme.surface
            },
            contentColor = theme.onSurface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TaskContent(
                task = task,
                theme = theme,
                context = context,
                isCompleted = isCompleted,
                modifier = Modifier.weight(1f)
            )

            if (!task.isDisabled) {
                TaskActions(
                    theme = theme,
                    onMarkDone = onMarkDone,
                    onMarkUndone = onMarkUndone
                )
            }
        }
    }
}

@Composable
private fun TaskContent(
    task: Task,
    theme: ThemeColor,
    context: Context,
    isCompleted: Boolean?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectionContainer {
            TaskTitle(
                title = task.title,
                isDisabled = task.isDisabled,
                theme = theme
            )
        }

        if (task.description.isNotEmpty()) {
            SelectionContainer {
                TaskDescription(
                    description = task.description,
                    isDisabled = task.isDisabled,
                    theme = theme
                )
            }
        }

        if (task.scheduledTime != 0L) {
            SelectionContainer {
                TaskScheduledTime(
                    scheduledTime = task.scheduledTime,
                    isDisabled = task.isDisabled,
                    theme = theme,
                    context = context
                )
            }
        }

        TaskBadges(
            isDisabled = task.isDisabled,
            isCompleted = isCompleted,
            theme = theme
        )
    }
}

@Composable
private fun TaskTitle(
    title: String,
    isDisabled: Boolean,
    theme: ThemeColor
) {
    Text(
        text = title.ifEmpty { stringResource(R.string.untitled_task) },
        fontSize = 17.nonScaledSp,
        fontWeight = FontWeight.SemiBold,
        color = if (isDisabled) {
            theme.onSurface.copy(alpha = 0.5f)
        } else {
            theme.onSurface
        },
        textDecoration = if (isDisabled) {
            TextDecoration.LineThrough
        } else {
            null
        }
    )

}

@Composable
private fun TaskDescription(
    description: String,
    isDisabled: Boolean,
    theme: ThemeColor
) {
    Text(
        text = description,
        fontSize = 14.nonScaledSp,
        lineHeight = 20.nonScaledSp,
        color = if (isDisabled) {
            theme.onSurface.copy(alpha = 0.4f)
        } else {
            theme.onSurface.copy(alpha = 0.75f)
        },
        textDecoration = if (isDisabled) {
            TextDecoration.LineThrough
        } else {
            null
        }
    )

}

@Composable
private fun TaskScheduledTime(
    scheduledTime: Long,
    isDisabled: Boolean,
    theme: ThemeColor,
    context: Context
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = if (isDisabled) {
                theme.onSurface.copy(alpha = 0.4f)
            } else {
                theme.onSurface
            }
        )
        Text(
            text = formatDate(scheduledTime, context),
            fontSize = 13.nonScaledSp,
            color = if (isDisabled) {
                theme.onSurface.copy(alpha = 0.4f)
            } else {
                theme.onSurface
            },
            textDecoration = if (isDisabled) {
                TextDecoration.LineThrough
            } else {
                null
            }
        )
    }
}

@Composable
private fun TaskBadges(
    isDisabled: Boolean,
    isCompleted: Boolean?,
    theme: ThemeColor
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isDisabled) {
            DisabledBadge(theme = theme)
        }

        isCompleted?.let {
            CompletedBadge(isCompleted = it, theme = theme)
        }
    }
}

@Composable
private fun DisabledBadge(theme: ThemeColor) {
    Surface(
        color = theme.onSurface.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(R.string.disabled),
            fontSize = 11.nonScaledSp,
            fontWeight = FontWeight.Medium,
            color = theme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun CompletedBadge(
    isCompleted: Boolean,
    theme: ThemeColor
) {
    Surface(
        color = if (isCompleted) {
            theme.greenOnSecondary.copy(alpha = 0.15f)
        } else {
            theme.redOnSecondary.copy(alpha = 0.15f)
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isCompleted) {
                    Icons.Default.CheckCircle
                } else {
                    Icons.Default.Close
                },
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = if (isCompleted) {
                    theme.greenOnSecondary
                } else {
                    theme.redOnSecondary
                }
            )
            Text(
                text = stringResource(
                    if (isCompleted) R.string.completed else R.string.not_completed
                ),
                fontSize = 11.nonScaledSp,
                fontWeight = FontWeight.Medium,
                color = if (isCompleted) {
                    theme.greenOnSecondary
                } else {
                    theme.redOnSecondary
                }
            )
        }
    }
}

@Composable
private fun TaskActions(
    theme: ThemeColor,
    onMarkDone: () -> Unit,
    onMarkUndone: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionButton(
            onClick = onMarkDone,
            icon = Icons.Default.Check,
            contentDescription = "Mark as done",
            backgroundColor = theme.greenOnSecondary.copy(alpha = 0.15f),
            contentColor = theme.greenOnSecondary
        )

        ActionButton(
            onClick = onMarkUndone,
            icon = Icons.Default.Close,
            contentDescription = "Mark as undone",
            backgroundColor = theme.redOnSecondary.copy(alpha = 0.15f),
            contentColor = theme.redOnSecondary
        )
    }
}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
