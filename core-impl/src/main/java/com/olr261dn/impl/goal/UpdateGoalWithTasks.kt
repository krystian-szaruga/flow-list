package com.olr261dn.impl.goal

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseInput


internal class UpdateGoalWithTasks(
    private val repository: GoalRepository
) : UseCaseInput<GoalWithTasks> {
    override suspend fun execute(param: GoalWithTasks) =
        repository.updateGoalWithTasks(param.project, param.tasks)
}