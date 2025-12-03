package com.olr261dn.task.stats
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.DisplayerOfAllStatisticItems
import com.olr261dn.ui.compose.common.ScaffoldWithTopBar
import com.olr261dn.viewmodel.StatsViewModel

@Composable
internal fun TaskStatsScreen(
    stats: Stats,
    viewModel: StatsViewModel,
    statsItems: List<CompletionHistory>,
    onBackPress: () -> Unit) {

    ScaffoldWithTopBar(
        title = stringResource(R.string.tasks_statistic_title),
        onBackPress = onBackPress,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OverviewCard(stats)
            }

            item {
                PieChartCard(stats)
            }

            item {
                CompletionTrendCard(statsItems)
            }

            item {
                DailyBreakdownCard(statsItems)
            }
            item {
                DisplayerOfAllStatisticItems(
                    stats = statsItems,
                    modifier = Modifier,
                    onUpdateCompletion = { viewModel.update(it) }
                )
            }
        }
    }
}


