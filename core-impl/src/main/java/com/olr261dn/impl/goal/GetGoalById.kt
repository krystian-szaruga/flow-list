package com.olr261dn.impl.goal

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetGoalById(
    private val repository: GoalRepository
) : UseCaseInputOutput<UUID, Project?> {
    override fun execute(param: UUID): Flow<Project?> = repository.getGoalById(param)
}