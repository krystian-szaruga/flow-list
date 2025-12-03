package com.olr261dn.project.details.utils

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.Project
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp


@Composable
internal fun GoalDetailCard(
    project: Project,
    theme: ThemeColor,
    context: Context,
    isCompleted: Boolean?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.secondary,
            contentColor = theme.onSecondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            GoalMainInfo(
                project = project,
                theme = theme,
                isCompleted = isCompleted
            )

            Spacer(modifier = Modifier.height(4.dp))

            GoalMetadata(
                project = project,
                theme = theme,
                context = context
            )
        }
    }
}

@Composable
private fun GoalMainInfo(
    project: Project,
    theme: ThemeColor,
    isCompleted: Boolean?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GoalTitleWithStatus(
            title = project.title,
            isCompleted = isCompleted,
            theme = theme
        )
        SelectionContainer {
            Text(
                text = project.description,
                fontSize = 16.nonScaledSp,
                lineHeight = 22.nonScaledSp,
                color = theme.onSecondary.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun GoalTitleWithStatus(
    title: String,
    isCompleted: Boolean?,
    theme: ThemeColor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectionContainer {
            Text(
                text = title.ifEmpty { stringResource(R.string.no_title) },
                fontSize = 26.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onSecondary
            )
        }

        isCompleted?.let {
            CompletionStatusIndicator(
                isCompleted = it,
                theme = theme
            )
        }
    }
}

@Composable
private fun CompletionStatusIndicator(
    isCompleted: Boolean,
    theme: ThemeColor
) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(
                color = if (isCompleted) theme.greenOnSecondary else theme.redOnSecondary,
                shape = CircleShape
            )
    )
}

@Composable
private fun GoalMetadata(
    project: Project,
    theme: ThemeColor,
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GoalScheduledDate(
            scheduledTime = project.scheduledTime,
            theme = theme,
            context = context
        )

        GoalStatusBadge(
            isDisabled = project.isDisabled,
            theme = theme
        )
    }
}

@Composable
private fun GoalScheduledDate(
    scheduledTime: Long,
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
            modifier = Modifier.size(18.dp),
            tint = theme.onSecondary.copy(alpha = 0.7f)
        )
        Text(
            text = formatDate(scheduledTime, context),
            fontSize = 14.nonScaledSp,
            color = theme.onSecondary.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun GoalStatusBadge(
    isDisabled: Boolean,
    theme: ThemeColor
) {
    Surface(
        color = if (isDisabled) {
            theme.redOnSecondary.copy(alpha = 0.15f)
        } else {
            theme.greenOnSecondary.copy(alpha = 0.15f)
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isDisabled) theme.redOnSecondary else theme.greenOnSecondary,
                        shape = CircleShape
                    )
            )
            Text(
                text = stringResource(if (isDisabled) R.string.disabled else R.string.active),
                fontSize = 12.nonScaledSp,
                fontWeight = FontWeight.Medium,
                color = if (isDisabled) theme.redOnSecondary else theme.greenOnSecondary
            )
        }
    }
}