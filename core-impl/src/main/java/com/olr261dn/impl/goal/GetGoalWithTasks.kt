package com.olr261dn.impl.goal

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetGoalWithTasks(
    private val repository: GoalRepository
) : UseCaseInputOutput<UUID, GoalWithTasks?> {
    override fun execute(param: UUID): Flow<GoalWithTasks?> =
        repository.getGoalWithTasks(param)
}