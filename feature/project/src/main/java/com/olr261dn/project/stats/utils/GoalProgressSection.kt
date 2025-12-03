package com.olr261dn.project.stats.utils

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun GoalProgressSection(stats: GoalStatsData, theme: ThemeColor) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.goal_completions),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.nonScaledSp,
            fontWeight = FontWeight.SemiBold,
            color = theme.onSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    R.string.completion_entries_format,
                    stats.goalCompletionCount,
                    stats.goalTotalEntries
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.nonScaledSp,
                color = theme.onSurface.copy(alpha = 0.8f)
            )

            if (stats.goalTotalEntries > 0) {
                Text(
                    text = "${(stats.goalCompletionCount.toFloat() / stats.goalTotalEntries * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.nonScaledSp,
                    fontWeight = FontWeight.Medium,
                    color = theme.primary
                )
            }
        }

        if (stats.goalTotalEntries > 0) {
            LinearProgressIndicator(
                progress = { stats.goalCompletionCount.toFloat() / stats.goalTotalEntries },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = theme.primary,
                trackColor = theme.onSurface.copy(alpha = 0.1f),
                strokeCap = StrokeCap.Round,
            )
        }
    }
}