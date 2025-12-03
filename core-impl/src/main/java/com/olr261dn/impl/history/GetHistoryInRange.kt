package com.olr261dn.impl.history

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow

internal class GetHistoryInRange(
    private val repository: CompletionHistoryRepository
) : UseCaseInputOutput<Pair<Long, Long>, List<CompletionHistory>> {
    override fun execute(param: Pair<Long, Long>): Flow<List<CompletionHistory>> =
        repository.getHistoryInRange(param.first, param.second)
}