package com.olr261dn.impl.history

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetHistoryForTask(
    private val repository: CompletionHistoryRepository
) : UseCaseInputOutput<UUID, List<CompletionHistory>> {
    override fun execute(param: UUID): Flow<List<CompletionHistory>> =
        repository.getHistoryForTask(param)
}