package com.olr261dn.data.repository

import com.olr261dn.data.database.dao.GoalDao
import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.mapper.toDomain
import com.olr261dn.data.mapper.toEntity
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

internal class GoalRepositoryImpl(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao
) : GoalRepository {

    override suspend fun insertGoal(project: Project) {
        goalDao.insert(project.toEntity())
    }

    override suspend fun updateGoal(project: Project) {
        goalDao.update(project.toEntity())
    }

    suspend fun upsertGoal(project: Project) {
        goalDao.upsert(project.toEntity())
    }

    override suspend fun deleteGoal(goalIds: List<UUID>) {
        goalDao.delete(goalIds)
    }

    override fun getGoalById(goalId: UUID): Flow<Project?> {
        return goalDao.getById(goalId).map { it?.toDomain() }
    }

    override fun getAllGoals(): Flow<List<Project>> {
        return goalDao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getGoalWithTasks(goalId: UUID): Flow<GoalWithTasks?> {
        return goalDao.getGoalWithTasks(goalId).map { it?.toDomain() }
    }

    override fun getAllGoalsWithTasks(): Flow<List<GoalWithTasks>> {
        return goalDao.getAllGoalsWithTasks().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertGoalWithTasks(project: Project, tasks: List<Task>) {
        insertGoal(project)
        tasks.forEach { task -> taskDao.insert(task.toEntity().copy(goalId = project.id)) }
    }

    override suspend fun updateGoalWithTasks(project: Project, tasks: List<Task>) {
        upsertGoal(project)
        tasks.forEach { task -> taskDao.upsert(task.copy(goalId = project.id).toEntity()) }
    }

    override fun getIds(): Flow<List<UUID>> {
        return goalDao.getIds()
    }
}