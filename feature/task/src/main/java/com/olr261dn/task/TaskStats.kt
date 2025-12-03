package com.olr261dn.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.task.stats.Stats
import com.olr261dn.task.stats.TaskStatsScreen
import com.olr261dn.viewmodel.StatsViewModel

internal object TaskStats {
    @Composable
    fun Content(onBackPress: () -> Unit) {
        val viewModel = hiltViewModel<StatsViewModel>()
        val statsItems = viewModel.standaloneTasks.collectAsState()
        val stats = remember(statsItems.value) {
            calculateTaskStats(statsItems.value)
        }
        TaskStatsScreen(
            stats = stats,
            viewModel = viewModel,
            statsItems = statsItems.value,
            onBackPress = onBackPress
        )
    }

    private fun calculateTaskStats(items: List<CompletionHistory>): Stats {
        val total = items.size
        val completed = items.count { it.isCompleted }
        val incomplete = total - completed
        val completionRate = if (total > 0) (completed.toFloat() / total * 100) else 0f
        return Stats(total, completed, incomplete, completionRate)
    }

}