package com.olr261dn.impl.position

import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class SetNavBarPosition(
    private val repository: UserPreferencesRepository
): UseCaseInput<NavbarPosition> {
    override suspend fun execute(param: NavbarPosition) {
        repository.setNavBarPosition(param)
    }
}