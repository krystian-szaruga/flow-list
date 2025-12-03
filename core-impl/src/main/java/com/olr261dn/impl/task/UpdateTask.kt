package com.olr261dn.impl.task

import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class UpdateTask(private val repository: TaskRepository): UseCaseInput<Task> {
    override suspend fun execute(param: Task) = repository.update(param)
}