package com.olr261dn.impl.history

import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class CountCompletionsForAnyType(
    private val repository: CompletionHistoryRepository
) : UseCaseInputOutput<UUID, Int> {
    override fun execute(param: UUID): Flow<Int> =
        repository.countCompletionsForAnyType(param)
}