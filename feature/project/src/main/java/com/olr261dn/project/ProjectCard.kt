package com.olr261dn.project

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.Project
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun GoalCard(
    project: Project,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = customTheme()

    val backgroundColor by animateColorAsState(
        targetValue = theme.getSurface(isSelected)
    )

    Surface(
        modifier = modifier
            .padding(top = 5.dp, bottom = 5.dp, end = 2.dp, start = 2.dp)
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(theme.primary.copy(alpha = 0.2f), theme.secondary.copy(alpha = 0.2f)),
                        startX = 0f,
                        endX = 1000f
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            GoalHeader(project.title, project.isDisabled, theme)
            GoalDescription(project.description, theme)
            GoalTimeInfo(project.scheduledTime, project.delayedTime, theme)
        }
    }
}


@Composable
private fun GoalHeader(
    title: String,
    isCompleted: Boolean,
    theme: ThemeColor
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.nonScaledSp,
            color = if (isCompleted) theme.onTertiary else theme.onSurface,
            textDecoration = if (isCompleted) TextDecoration.LineThrough else null
        )

        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Completed",
                tint = theme.onSurface
            )
        }
    }
}

@Composable
private fun GoalDescription(
    text: String,
    theme: ThemeColor
) {
    Text(
        text = text,
        fontSize = 14.nonScaledSp,
        color = theme.onSurface.copy(alpha = 0.7f),
        modifier = Modifier.padding(top = 8.dp),
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun GoalTimeInfo(
    scheduledTime: Long,
    delayedTime: Long?,
    theme: ThemeColor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimeChip(
            timestamp = scheduledTime,
            theme = theme,
            isDelayed = delayedTime != null && delayedTime < System.currentTimeMillis()
        )
    }
}


@Composable
private fun TimeChip(
    timestamp: Long,
    theme: ThemeColor,
    isDelayed: Boolean
) {
    val context = LocalContext.current
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isDelayed) theme.redOnSecondary.copy(alpha = 0.2f) else theme.secondary.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isDelayed) theme.redOnSecondary else theme.onSecondary
            )
            Text(
                text = formatDate(timestamp, context),
                fontSize = 12.nonScaledSp,
                color = if (isDelayed) theme.redOnSecondary else theme.onSecondary
            )
        }
    }
}
