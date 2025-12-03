package com.olr261dn.impl.delay

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetTaskDelay(
    private val repository: UserPreferencesRepository
): UseCaseReturnOnly<Long> {
    override fun execute(): Flow<Long> = repository.getTaskDelay()
}