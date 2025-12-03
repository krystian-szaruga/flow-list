package com.olr261dn.data.repository

import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.mapper.toDomain
import com.olr261dn.data.mapper.toEntity
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

internal class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override suspend fun insert(param: Task) {
        taskDao.insert(param.toEntity())
    }

    override suspend fun update(param: Task) {
        taskDao.update(param.toEntity())
    }

    override fun getById(id: UUID): Flow<Task?> = taskDao.getById(id).map { it?.toDomain() }

    override fun getAll(): Flow<List<Task>> {
        return taskDao.getAll().map { list -> list.map { it.toDomain() } }
    }
    override fun getStandaloneTasks(): Flow<List<Task>> =
        taskDao.getStandaloneTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun delete(param: List<UUID>) {
        taskDao.delete(param)
    }

    override fun getIds(): Flow<List<UUID>> = taskDao.getIds()
}