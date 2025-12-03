package com.olr261dn.impl.screen

import com.olr261dn.domain.repository.UserPreferencesRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class SetDefaultScreen(private val repository: UserPreferencesRepository): UseCaseInput<String> {
    override suspend fun execute(param: String) { repository.setDefaultScreen(param) }
}