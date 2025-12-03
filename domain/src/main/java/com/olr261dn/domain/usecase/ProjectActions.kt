package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import java.util.UUID

class ProjectActions(
    override val add: UseCaseInput<Project>,
    override val get: UseCaseReturnOnly<List<Project>>,
    override val getById: UseCaseInputOutput<UUID, Project?>,
    override val remove: UseCaseInput<List<UUID>>,
    override val update: UseCaseInput<Project>,
    val getWithTasks: UseCaseInputOutput<UUID, GoalWithTasks?>,
    val getAllWithTasks: UseCaseReturnOnly<List<GoalWithTasks>>,
    val addWithTasks: UseCaseInput<GoalWithTasks>,
    val updateGoalWithTasks: UseCaseInput<GoalWithTasks>,
    val getIds: UseCaseReturnOnly<List<UUID>>
) : BaseItemActions<Project>()