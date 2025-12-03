package com.olr261dn.impl.position

import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetNavBarPosition(
    private val repository: UserPreferencesRepository
): UseCaseReturnOnly<NavbarPosition> {
    override fun execute(): Flow<NavbarPosition> = repository.getNavBarPosition()
}