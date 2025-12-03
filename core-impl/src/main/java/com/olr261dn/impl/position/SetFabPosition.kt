package com.olr261dn.impl.position

import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class SetFabPosition(
    private val repository: UserPreferencesRepository
): UseCaseInput<QuickActionPosition> {
    override suspend fun execute(param: QuickActionPosition) {
        repository.setFabPosition(param)
    }
}