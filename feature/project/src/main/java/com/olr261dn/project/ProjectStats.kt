package com.olr261dn.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.project.stats.GoalStatsAggregator
import com.olr261dn.project.stats.GoalStatsBuilder
import com.olr261dn.project.stats.StatisticScreen
import com.olr261dn.viewmodel.GoalItemViewModel
import com.olr261dn.viewmodel.StatsViewModel

internal object ProjectStats {
    @Composable
    fun Show(onBack: () -> Unit) {
        val statsViewModel: StatsViewModel = hiltViewModel()
        val goalViewModel: GoalItemViewModel = hiltViewModel()

        val goals by statsViewModel.goals.collectAsState()
        val tasksLinkedToGoal by statsViewModel.tasksLinkedToGoal.collectAsState()
        val items by goalViewModel.items.collectAsState()
        val goalWithTasks by goalViewModel.goalsWithTasks.collectAsState()

        val goalStatsMap = remember(goals, tasksLinkedToGoal, items, goalWithTasks) {
            GoalStatsBuilder(goals, tasksLinkedToGoal, items, goalWithTasks).build()
        }

        val aggregatedStats = remember(goalStatsMap) {
            GoalStatsAggregator.aggregate(goalStatsMap)
        }

        StatisticScreen(
            onBackPress = onBack,
            goalStatsMap = goalStatsMap,
            aggregatedStats = aggregatedStats
        )
    }
}


