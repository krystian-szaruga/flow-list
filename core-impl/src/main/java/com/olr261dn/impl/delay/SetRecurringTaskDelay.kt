package com.olr261dn.impl.delay

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class SetRecurringTaskDelay(
    private val repository: UserPreferencesRepository
): UseCaseInput<Long> {
    override suspend fun execute(param: Long) {
        repository.setRecurringTaskDelay(param)
    }
}