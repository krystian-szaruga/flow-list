package com.olr261dn.impl.task

import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetStandaloneTasks(private val repository: TaskRepository): UseCaseReturnOnly<List<Task>> {
    override fun execute(): Flow<List<Task>> = repository.getStandaloneTasks()
}