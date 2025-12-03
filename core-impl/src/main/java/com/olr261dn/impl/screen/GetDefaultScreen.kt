package com.olr261dn.impl.screen

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetDefaultScreen(private val repository: UserPreferencesRepository
): UseCaseReturnOnly<String?> {
    override fun execute(): Flow<String?> = repository.getDefaultScreen()
}