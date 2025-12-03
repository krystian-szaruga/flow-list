package com.olr261dn.impl.goal

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetAllGoals(
    private val repository: GoalRepository
) : UseCaseReturnOnly<List<Project>> {
    override fun execute(): Flow<List<Project>> = repository.getAllGoals()
}
