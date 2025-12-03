package com.olr261dn.domain.repository

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GoalRepository : BaseRepository {
    suspend fun insertGoal(project: Project)
    suspend fun insertGoalWithTasks(project: Project, tasks: List<Task>)
    suspend fun updateGoalWithTasks(project: Project, tasks: List<Task>)
    suspend fun updateGoal(project: Project)
    suspend fun deleteGoal(goalIds: List<UUID>)
    fun getGoalById(goalId: UUID): Flow<Project?>
    fun getAllGoals(): Flow<List<Project>>
    fun getGoalWithTasks(goalId: UUID): Flow<GoalWithTasks?>
    fun getAllGoalsWithTasks(): Flow<List<GoalWithTasks>>
}