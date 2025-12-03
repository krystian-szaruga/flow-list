package com.olr261dn.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatDate
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.StatsViewModel
import java.util.UUID

@Composable
internal fun DetailsContent(item: Task, onToggleCompletion: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TitleSection(title = item.title)
        ScheduledTimeSection(scheduledTime = item.scheduledTime)
        DescriptionSection(description = item.description)
        item.goalId?.let { goalId ->
            LinkedGoalSection(goalId = goalId)
        }
        StatusSection(isDisabled = item.isDisabled)
        HistoryCompletionSection(id = item.id)
    }
}

@Composable
private fun TitleSection(title: String) {
    val theme = customTheme()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.secondary,
            contentColor = theme.onSecondary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 12.nonScaledSp,
            )
            SelectionContainer {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 24.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun ScheduledTimeSection(scheduledTime: Long) {
    val theme = customTheme()

    InfoCard(
        icon = Icons.Default.DateRange,
        containerColor = theme.secondary,
        onContainerColor = theme.onSecondary,
        label = stringResource(R.string.scheduled),
        value = formatDate(scheduledTime, LocalContext.current),
    )
}

@Composable
private fun DescriptionSection(description: String) {
    val theme = customTheme()

    if (description.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = theme.secondary,
                contentColor = theme.onSecondary
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(R.string.description),
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.nonScaledSp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                SelectionContainer {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.nonScaledSp,
                        lineHeight = 24.nonScaledSp,
                    )
                }
            }
        }
    } else {
        EmptyDescriptionCard()
    }
}

@Composable
private fun EmptyDescriptionCard() {
    val theme = customTheme()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.surface.copy(alpha = 0.5f),
            contentColor = theme.onSurface
        )
    ) {
        Text(
            text = stringResource(R.string.no_description),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.nonScaledSp,
            modifier = Modifier.padding(16.dp),
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
private fun LinkedGoalSection(goalId: UUID) {
    val theme = customTheme()

    InfoCard(
        icon = Icons.Default.Star,
        containerColor = theme.tertiary,
        onContainerColor = theme.onTertiary,
        label = stringResource(R.string.linked_goal),
        value = goalId.toString().take(8) + "...",
    )
}

@Composable
private fun InfoCard(
    icon: ImageVector,
    containerColor: Color,
    onContainerColor: Color,
    label: String,
    value: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = onContainerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 12.nonScaledSp,
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.nonScaledSp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun StatusSection(isDisabled: Boolean) {
    val theme = customTheme()
    StatusIndicator(
        isActive = !isDisabled,
        activeText = R.string.enabled,
        inactiveText = R.string.disabled,
        activeColor = theme.greenOnSecondary,
        inactiveColor = theme.redOnSecondary,
    )
}

@Composable
private fun HistoryCompletionSection(id: UUID) {
    val theme = customTheme()
    val statsViewModel = hiltViewModel<StatsViewModel>()
    val lastStatus by statsViewModel.standaloneTaskLastStatus().collectAsState(initial = null)
    val isCompleted = lastStatus?.isCompleted ?: false

    StatusIndicator(
        isActive = isCompleted,
        activeText = R.string.completed,
        inactiveText = R.string.not_completed,
        activeColor = theme.greenOnSecondary,
        inactiveColor = theme.redOnSecondary
    )
}

@Composable
private fun StatusIndicator(
    isActive: Boolean,
    activeText: Int,
    inactiveText: Int,
    activeColor: Color,
    inactiveColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = if (isActive) activeColor else inactiveColor,
                        shape = CircleShape
                    )
            )
            Text(
                text = stringResource(if (isActive) activeText else inactiveText),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.nonScaledSp,
                fontWeight = FontWeight.SemiBold,
                color = if (isActive) activeColor else inactiveColor
            )
        }
    }
}