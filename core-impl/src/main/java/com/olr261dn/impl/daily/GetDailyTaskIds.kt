package com.olr261dn.impl.daily

import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetDailyTaskIds(private val repository: DailyRoutineRepository): UseCaseReturnOnly<List<UUID>> {
    override fun execute(): Flow<List<UUID>> = repository.getIds()
}