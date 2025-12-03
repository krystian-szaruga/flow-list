package com.olr261dn.impl.security

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class SetBiometricStatus(
    private val repository: UserPreferencesRepository
): UseCaseInput<Boolean> {
    override suspend fun execute(param: Boolean) {
        repository.setBiometricStatus(param)
    }
}