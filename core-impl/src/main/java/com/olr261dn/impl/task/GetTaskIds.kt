package com.olr261dn.impl.task

import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetTaskIds(private val repository: TaskRepository): UseCaseReturnOnly<List<UUID>> {
    override fun execute(): Flow<List<UUID>> = repository.getIds()
}