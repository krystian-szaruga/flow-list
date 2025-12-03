package com.olr261dn.project.stats.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun AggregatedStatsCard(
    stats: GoalStatsData,
    totalGoals: Int
) {
    val theme = customTheme()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = theme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.all_goals),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.nonScaledSp,
                        color = theme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.total_goals_format, totalGoals),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.nonScaledSp,
                        color = theme.onSurface.copy(alpha = 0.6f)
                    )
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

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = theme.onSurface.copy(alpha = 0.12f)
            )

            GoalProgressSection(stats, theme)

            Spacer(modifier = Modifier.height(4.dp))

            LinkedTasksSection(stats, theme)


            if (stats.lastGoalUpdate != null || stats.lastTaskUpdate != null) {
                Spacer(modifier = Modifier.height(4.dp))
                LastUpdatesSection(stats, theme)
            }
        }
    }
}