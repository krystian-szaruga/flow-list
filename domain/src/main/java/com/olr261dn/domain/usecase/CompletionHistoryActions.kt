package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.CompletionHistory
import java.util.UUID

data class CompletionHistoryActions(
    val add: UseCaseInput<CompletionHistory>,
    val update: UseCaseInput<CompletionHistory>,
    val addAll: UseCaseInput<List<CompletionHistory>>,
    val getAll: UseCaseReturnOnly<List<CompletionHistory>>,
    val getForDaily: UseCaseInputOutput<UUID, List<CompletionHistory>>,
    val getForTask: UseCaseInputOutput<UUID, List<CompletionHistory>>,
    val getForStandaloneGoal: UseCaseInputOutput<UUID, List<CompletionHistory>>,
    val getInRange: UseCaseInputOutput<Pair<Long, Long>, List<CompletionHistory>>,
    val countCompletionsForAnyType: UseCaseInputOutput<UUID, Int>
)