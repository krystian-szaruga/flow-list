package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.Task
import java.util.UUID

class TaskActions (
    override val add: UseCaseInput<Task>,
    override val get: UseCaseReturnOnly<List<Task>>,
    val getStandaloneTasks: UseCaseReturnOnly<List<Task>>,
    override val getById: UseCaseInputOutput<UUID, Task?>,
    override val remove: UseCaseInput<List<UUID>>,
    override val update: UseCaseInput<Task>
): BaseItemActions<Task>()