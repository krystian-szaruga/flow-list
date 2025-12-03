package com.olr261dn.impl.goal

import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetAllGoalsWithTasks(
    private val repository: GoalRepository
) : UseCaseReturnOnly<List<GoalWithTasks>> {
    override fun execute(): Flow<List<GoalWithTasks>> =
        repository.getAllGoalsWithTasks()
}