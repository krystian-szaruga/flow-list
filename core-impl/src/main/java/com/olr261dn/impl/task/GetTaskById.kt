package com.olr261dn.impl.task

import com.olr261dn.domain.model.Task
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetTaskById(private val repository: TaskRepository):
    UseCaseInputOutput<UUID, Task?> {
    override fun execute(param: UUID): Flow<Task?> = repository.getById(param)
}