package com.olr261dn.impl.daily

import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.usecase.UseCaseInput
import java.util.UUID

internal class RemoveDailyTask(private val repository: DailyRoutineRepository):
    UseCaseInput<List<UUID>> {
    override suspend fun execute(param: List<UUID>) = repository.delete(param)
}