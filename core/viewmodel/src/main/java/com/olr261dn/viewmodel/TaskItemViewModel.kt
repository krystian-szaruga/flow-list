package com.olr261dn.viewmodel

import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.usecase.TaskActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TaskItemViewModel @Inject constructor(actions: TaskActions): ItemViewModel<Task>(actions) {
    val tasks: StateFlow<List<Task>> = actions.get.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    override val items: StateFlow<List<Task>> = actions.getStandaloneTasks.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override fun sortedItems(items: List<Task>): List<Task> = sortSchedulable(items)
}