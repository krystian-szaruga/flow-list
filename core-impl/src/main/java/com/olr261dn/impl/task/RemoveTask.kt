package com.olr261dn.impl.task

import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseInput
import java.util.UUID

internal class RemoveTask(private val repository: TaskRepository): UseCaseInput<List<UUID>> {
    override suspend fun execute(param: List<UUID>) = repository.delete(param)
}