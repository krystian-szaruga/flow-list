package com.olr261dn.impl.position

import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetFabPosition(
    private val repository: UserPreferencesRepository
): UseCaseReturnOnly<QuickActionPosition> {
    override fun execute(): Flow<QuickActionPosition> = repository.getFabPosition()
}