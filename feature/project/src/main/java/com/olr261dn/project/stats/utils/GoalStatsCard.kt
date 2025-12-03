package com.olr261dn.project.stats.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.ui.theme.customTheme

@Composable
internal fun GoalStatsCard(
    stats: GoalStatsData,
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
            GoalOverviewSection(stats, theme)

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
