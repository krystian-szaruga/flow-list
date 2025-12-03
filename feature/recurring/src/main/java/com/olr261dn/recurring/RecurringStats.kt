package com.olr261dn.recurring

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.viewmodel.RecurringTaskItemViewModel
import java.util.UUID

object RecurringStats: BaseStatsScreen<RecurringTask, RecurringTaskItemViewModel>() {

    override fun idExtractor(statsItem: CompletionHistory): UUID? = statsItem.recurringTaskId
    @Composable
    fun Content(onBackClick: () -> Unit) {
        val itemViewModel = hiltViewModel<RecurringTaskItemViewModel>()
        Screen(itemViewModel, onBackClick) { it.dailyTasks }
    }
}
