package com.olr261dn.impl.history

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class AddCompletionHistory(
    private val repository: CompletionHistoryRepository
) : UseCaseInput<CompletionHistory> {
    override suspend fun execute(param: CompletionHistory) =
        repository.insert(param)
}