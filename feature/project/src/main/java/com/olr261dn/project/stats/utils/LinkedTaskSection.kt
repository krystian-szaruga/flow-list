package com.olr261dn.project.stats.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun LinkedTasksSection(stats: GoalStatsData, theme: ThemeColor) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle(
            text = stringResource(R.string.linked_tasks),
            theme = theme
        )

        TaskProgressItem(
            labelRes = R.string.tasks_completed_format,
            count = stats.completedEntriesCount,
            totalCount = stats.totalHistoryEntriesCount,
            color = customTheme().greenOnSecondary,
            theme = theme
        )

        TaskProgressItem(
            labelRes = R.string.tasks_not_completed_format,
            count = stats.notCompletedEntriesCount,
            totalCount = stats.totalHistoryEntriesCount,
            color = customTheme().redOnSecondary,
            theme = theme
        )

        TaskProgressItem(
            labelRes = R.string.tasks_not_marked_format,
            count = stats.tasksWithoutHistoryCount,
            totalCount = stats.totalLinkedTasksCount,
            color = customTheme().orangeOnSecondary,
            theme = theme
        )
    }
}

@Composable
private fun SectionTitle(
    text: String,
    theme: ThemeColor
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontSize = 14.nonScaledSp,
        fontWeight = FontWeight.SemiBold,
        color = theme.onSurface
    )
}

@Composable
private fun TaskProgressItem(
    @StringRes labelRes: Int,
    count: Int,
    totalCount: Int,
    color: Color,
    theme: ThemeColor
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        TaskProgressHeader(
            labelRes = labelRes,
            count = count,
            totalCount = totalCount,
            color = color,
            theme = theme
        )

        if (totalCount > 0) {
            TaskProgressBar(
                progress = count.toFloat() / totalCount,
                color = color,
                theme = theme
            )
        }
    }
}

@Composable
private fun TaskProgressHeader(
    @StringRes labelRes: Int,
    count: Int,
    totalCount: Int,
    color: Color,
    theme: ThemeColor
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(labelRes, count, totalCount),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 13.nonScaledSp,
            color = theme.onSurface.copy(alpha = 0.8f)
        )

        if (totalCount > 0) {
            PercentageText(
                percentage = calculatePercentage(count, totalCount),
                color = color
            )
        }
    }
}

@Composable
private fun PercentageText(
    percentage: Int,
    color: Color
) {
    Text(
        text = "$percentage%",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 13.nonScaledSp,
        fontWeight = FontWeight.Medium,
        color = color
    )
}

@Composable
private fun TaskProgressBar(
    progress: Float,
    color: Color,
    theme: ThemeColor
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = color,
        trackColor = theme.onSurface.copy(alpha = 0.1f),
        strokeCap = StrokeCap.Round,
    )
}

private fun calculatePercentage(count: Int, total: Int): Int {
    return if (total > 0) {
        (count.toFloat() / total * 100).toInt()
    } else {
        0
    }
}