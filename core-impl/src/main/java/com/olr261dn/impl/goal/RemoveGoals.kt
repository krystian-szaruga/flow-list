package com.olr261dn.impl.goal

import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseInput
import java.util.UUID

internal class RemoveGoals(
    private val repository: GoalRepository
) : UseCaseInput<List<UUID>> {
    override suspend fun execute(param: List<UUID>) = repository.deleteGoal(param)
}