package com.olr261dn.project.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.utils.AggregatedStatsCard
import com.olr261dn.project.stats.utils.GoalFilterDropdown
import com.olr261dn.project.stats.utils.GoalStatsCard
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.ScaffoldWithTopBar
import java.util.UUID

@Composable
internal fun StatisticScreen(
    onBackPress: () -> Unit,
    goalStatsMap: Map<UUID, GoalStatsData>,
    aggregatedStats: GoalStatsData
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedGoalId by remember { mutableStateOf<UUID?>(null) }

    ScaffoldWithTopBar(
        title = stringResource(R.string.goals_statistic_title),
        onBackPress = onBackPress,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            GoalFilterDropdown(
                goalStatsMap = goalStatsMap,
                selectedGoalId = selectedGoalId,
                isExpanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = it },
                onGoalSelected = {
                    selectedGoalId = it
                    isDropdownExpanded = false
                }
            )

            if (selectedGoalId == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AggregatedStatsCard(
                            stats = aggregatedStats,
                            totalGoals = goalStatsMap.size
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    goalStatsMap[selectedGoalId]?.let { stats ->
                        item {
                            GoalStatsCard(stats = stats)
                        }
                    }
                }
            }
        }
    }
}




