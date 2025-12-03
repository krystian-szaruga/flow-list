package com.olr261dn.viewmodel

import androidx.lifecycle.viewModelScope
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.usecase.ProjectActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoalItemViewModel @Inject constructor(
    private val actions: ProjectActions
) : ItemViewModel<Project>(actions) {

    val goalsWithTasks: StateFlow<List<GoalWithTasks>> =
        actions.getAllWithTasks.execute()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun addGoalWithTasks(goalWithTasksInput: GoalWithTasks) {
        launch {
            actions.addWithTasks.execute(goalWithTasksInput)
        }
    }

    fun updateGoalWithTasks(goalWithTasks: GoalWithTasks) {
        launch {
            actions.updateGoalWithTasks.execute(goalWithTasks)
        }
    }

    override fun sortedItems(items: List<Project>): List<Project> = sortSchedulable(items)

}
