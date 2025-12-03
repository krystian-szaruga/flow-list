package com.olr261dn.domain.repository

import com.olr261dn.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository: ItemRepository<Task> {
    fun getStandaloneTasks(): Flow<List<Task>>
}