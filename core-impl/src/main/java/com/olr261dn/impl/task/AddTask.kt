package com.olr261dn.impl.task

import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class AddTask(private val repository: TaskRepository)
    : UseCaseInput<Task> {
    override suspend fun execute(param: Task) = repository.insert(param)
}