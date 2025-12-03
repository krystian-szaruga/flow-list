package com.olr261dn.recurring

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.viewmodel.BaseItemViewModel
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.DetailedStatsCard
import com.olr261dn.ui.compose.common.DisplayerOfAllStatisticItems
import com.olr261dn.ui.compose.common.DropdownSelector
import com.olr261dn.ui.compose.common.ScaffoldWithTopBar
import com.olr261dn.viewmodel.StatsViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

abstract class BaseStatsScreen<T: Schedulable, VM: BaseItemViewModel<T>> {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    protected fun Screen(
        itemViewModel: BaseItemViewModel<T>,
        onBackClick: () -> Unit,
        dataSelector: (StatsViewModel) -> StateFlow<List<CompletionHistory>>
    ) {
        val title: String = stringResource(R.string.recurring_statistics)
        val viewModel = hiltViewModel<StatsViewModel>()
        val statsItems = dataSelector(viewModel).collectAsState()
        val items = itemViewModel.items.collectAsState()
        val groupedStats = remember(statsItems.value) {
            statsItems.value
                .groupBy { idExtractor(it) }
                .filterKeys { it != null }
        }
        var selectedItemId by remember { mutableStateOf<UUID?>(null) }
        var isDropdownExpanded by remember { mutableStateOf(false) }
        val selectedItem = selectedItemId?.let { id ->
            items.value.firstOrNull { it.id == id }
        }
        val selectedStats = selectedItem?.let { item ->
            groupedStats[item.id]
        }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(items.value) {
            if (selectedItemId == null && items.value.isNotEmpty()) {
                selectedItemId = items.value.first().id
            }
        }
        ScaffoldWithTopBar(
            title = title,
            onBackPress = onBackClick
        ) { padding ->
            LazyColumn(
                state = listState,
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.Companion.height(20.dp))
                    DropdownSelector(
                        items = items.value,
                        selectedItem = selectedItem,
                        isExpanded = isDropdownExpanded,
                        onExpandChange = { isDropdownExpanded = it },
                        onItemSelected = { item ->
                            selectedItemId = item.id
                            isDropdownExpanded = false
                        }
                    )
                    Spacer(modifier = Modifier.Companion.height(24.dp))
                }
                selectedItem?.let { item ->
                    selectedStats?.let { stats ->
                        val total = stats.size
                        val completed = stats.count { it.isCompleted }
                        val percentage = if (total > 0) completed.toFloat() / total else 0f
                        item {
                            DetailedStatsCard(
                                percentage = percentage,
                                completed = completed,
                                total = total,
                                stats = stats,
                                onScrollToBottom = {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(
                                            listState.layoutInfo.totalItemsCount - 1
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    DisplayerOfAllStatisticItems(
                        stats = selectedStats,
                        onUpdateCompletion = { it ->
                            viewModel.update(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    protected abstract fun idExtractor(statsItem: CompletionHistory): UUID?
}