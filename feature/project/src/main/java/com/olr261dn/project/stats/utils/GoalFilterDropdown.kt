package com.olr261dn.project.stats.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import java.util.UUID

@Composable
internal fun GoalFilterDropdown(
    goalStatsMap: Map<UUID, GoalStatsData>,
    selectedGoalId: UUID?,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onGoalSelected: (UUID?) -> Unit
) {
    val theme = customTheme()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        color = theme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp
    ) {
        Column {
            GoalFilterHeader(
                selectedGoalTitle = getSelectedGoalTitle(goalStatsMap, selectedGoalId),
                isExpanded = isExpanded,
                onExpandedChange = onExpandedChange
            )

            GoalFilterMenu(
                isExpanded = isExpanded,
                goalStatsMap = goalStatsMap,
                selectedGoalId = selectedGoalId,
                onDismiss = { onExpandedChange(false) },
                onGoalSelected = onGoalSelected
            )
        }
    }
}

@Composable
private fun GoalFilterHeader(
    selectedGoalTitle: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val theme = customTheme()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandedChange(!isExpanded) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.filter_by_goal),
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 12.nonScaledSp,
                    color = theme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedGoalTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.nonScaledSp,
                    fontWeight = FontWeight.Medium,
                    color = theme.onSurface
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = theme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun GoalFilterMenu(
    isExpanded: Boolean,
    goalStatsMap: Map<UUID, GoalStatsData>,
    selectedGoalId: UUID?,
    onDismiss: () -> Unit,
    onGoalSelected: (UUID?) -> Unit
) {
    val theme = customTheme()

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(theme.surface)
    ) {
        AllGoalsMenuItem(
            isSelected = selectedGoalId == null,
            onClick = { onGoalSelected(null) }
        )

        HorizontalDivider(color = theme.onSurface.copy(alpha = 0.1f))

        goalStatsMap.entries.forEach { (goalId, stats) ->
            GoalMenuItem(
                stats = stats,
                isSelected = selectedGoalId == goalId,
                onClick = { onGoalSelected(goalId) }
            )
        }
    }
}

@Composable
private fun AllGoalsMenuItem(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val theme = customTheme()

    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectionIndicator(isSelected = isSelected)
                Text(
                    text = stringResource(R.string.all_goals),
                    fontSize = 14.nonScaledSp,
                    color = theme.onSurface,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        },
        onClick = onClick
    )
}

@Composable
private fun GoalMenuItem(
    stats: GoalStatsData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectionIndicator(isSelected = isSelected)

                GoalInfo(
                    stats = stats,
                    isSelected = isSelected,
                    modifier = Modifier.weight(1f)
                )

                CompletionBadge(stats = stats)
            }
        },
        onClick = onClick
    )
}

@Composable
private fun SelectionIndicator(isSelected: Boolean) {
    val theme = customTheme()

    if (isSelected) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = theme.primary
        )
    } else {
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun GoalInfo(
    stats: GoalStatsData,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val theme = customTheme()

    Column(modifier = modifier) {
        Text(
            text = stats.goalTitle,
            fontSize = 14.nonScaledSp,
            color = theme.onSurface,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
        GoalMetadata(stats = stats)
    }
}

@Composable
private fun GoalMetadata(stats: GoalStatsData) {
    val theme = customTheme()

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.tasks_count_format, stats.totalHistoryEntriesCount),
            fontSize = 11.nonScaledSp,
            color = theme.onSurface.copy(alpha = 0.6f)
        )
        if (stats.tasksWithoutHistoryCount > 0) {
            Text(
                text = "• ${stats.tasksWithoutHistoryCount} pending",
                fontSize = 11.nonScaledSp,
                color = theme.onSurface.copy(alpha = 0.5f),
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
private fun CompletionBadge(stats: GoalStatsData) {
    val theme = customTheme()
    val completionRate = calculateCompletionRate(stats)

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = theme.getCompletedColor(completionRate >= 50).copy(alpha = 0.1f)
    ) {
        Text(
            text = "$completionRate%",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 11.nonScaledSp,
            fontWeight = FontWeight.SemiBold,
            color = theme.getCompletedColor(completionRate >= 50)
        )
    }
}

@Composable
private fun getSelectedGoalTitle(
    goalStatsMap: Map<UUID, GoalStatsData>,
    selectedGoalId: UUID?
): String {
    return if (selectedGoalId == null) {
        stringResource(R.string.all_goals)
    } else {
        goalStatsMap[selectedGoalId]?.goalTitle ?: ""
    }
}

private fun calculateCompletionRate(stats: GoalStatsData): Int {
    return if (stats.goalTotalEntries > 0) {
        (stats.goalCompletionCount.toFloat() / stats.goalTotalEntries * 100).toInt()
    } else {
        0
    }
}