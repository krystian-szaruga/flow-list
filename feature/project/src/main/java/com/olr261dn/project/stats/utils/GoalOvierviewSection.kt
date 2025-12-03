package com.olr261dn.project.stats.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun GoalOverviewSection(stats: GoalStatsData, theme: ThemeColor) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stats.goalTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 18.nonScaledSp,
                color = theme.onSurface
            )
            if (stats.tasksWithoutHistoryCount > 0) {
                Text(
                    text = stats.tasksWithoutHistoryCount.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.nonScaledSp,
                    color = theme.onSurface.copy(alpha = 0.5f),
                    fontStyle = FontStyle.Italic
                )
            }
        }

        val completionRate = if (stats.goalTotalEntries > 0) {
            (stats.goalCompletionCount.toFloat() / stats.goalTotalEntries * 100).toInt()
        } else 0

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = theme.getCompletedColor(completionRate >= 50).copy(alpha = 0.1f)
        ) {
            Text(
                text = "$completionRate%",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                fontSize = 14.nonScaledSp,
                fontWeight = FontWeight.SemiBold,
                color = theme.getCompletedColor(completionRate >= 50)
            )
        }
    }
}