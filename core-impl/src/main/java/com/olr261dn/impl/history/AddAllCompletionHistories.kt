package com.olr261dn.impl.history

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class AddAllCompletionHistories(
    private val repository: CompletionHistoryRepository
) : UseCaseInput<List<CompletionHistory>> {
    override suspend fun execute(param: List<CompletionHistory>) =
        repository.insertAll(param)
}