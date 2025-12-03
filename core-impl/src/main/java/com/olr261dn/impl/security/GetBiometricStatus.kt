package com.olr261dn.impl.security

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetBiometricStatus(
    private val repository: UserPreferencesRepository
): UseCaseReturnOnly<Boolean> {
    override fun execute(): Flow<Boolean> = repository.getBiometricStatus()
}