package com.olr261dn.impl.goal

import com.olr261dn.domain.model.Project
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class AddGoals(
    private val repository: GoalRepository
) : UseCaseInput<Project> {
    override suspend fun execute(param: Project) = repository.insertGoal(param)
}