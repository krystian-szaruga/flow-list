package com.olr261dn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.usecase.CompletionHistoryActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val actions: CompletionHistoryActions
): ViewModel() {

    val items: StateFlow<List<CompletionHistory>> = actions.getAll.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val dailyTasks: StateFlow<List<CompletionHistory>> = items
        .map { list -> list.filter { it.recurringTaskId != null } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val goals: StateFlow<List<CompletionHistory>> = items
        .map { list -> list.filter { it.projectId != null } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val standaloneTasks: StateFlow<List<CompletionHistory>> = items
        .map { list -> list.filter { it.taskId != null && it.projectId == null } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasksLinkedToGoal: StateFlow<List<CompletionHistory>> = items
        .map { list -> list.filter { it.taskId != null && it.projectId != null } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun add(item: CompletionHistory) {
        viewModelScope.launch {
            actions.add.execute(item)
        }
    }

    fun update(item: CompletionHistory) {
        viewModelScope.launch {
            actions.update.execute(item)
        }
    }

    fun dailyTaskLastStatus(): Flow<CompletionHistory?> =
        dailyTasks.map { list -> list.maxByOrNull { it.markedAt } }


    fun standaloneTaskLastStatus(): Flow<CompletionHistory?> =
        standaloneTasks.map { list -> list.maxByOrNull { it.markedAt } }


    fun goalLastStatus(goalId: UUID): Flow<CompletionHistory?> {
        return goals.map { list ->
            list.filter { it.projectId == goalId && it.taskId == null }
                .maxByOrNull { it.markedAt }
        }
    }

    fun taskLinkedToGoalLastStatus(taskId: UUID, goalId: UUID?): Flow<CompletionHistory?> {
        return tasksLinkedToGoal.map { list ->
            list.filter { it.taskId == taskId && it.projectId == goalId }
                .maxByOrNull { it.markedAt }
        }
    }
}
