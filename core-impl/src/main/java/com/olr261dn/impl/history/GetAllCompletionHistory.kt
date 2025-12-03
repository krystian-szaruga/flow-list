package com.olr261dn.impl.history

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetAllCompletionHistory(
    private val repository: CompletionHistoryRepository
) : UseCaseReturnOnly<List<CompletionHistory>> {
    override fun execute(): Flow<List<CompletionHistory>> = repository.getAll()
}