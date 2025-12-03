package com.olr261dn.impl.goal

import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetGoalIds(
    private val repository: GoalRepository
) : UseCaseReturnOnly<List<UUID>> {
    override fun execute(): Flow<List<UUID>> = repository.getIds()
}