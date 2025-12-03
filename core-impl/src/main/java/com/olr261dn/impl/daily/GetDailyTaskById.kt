package com.olr261dn.impl.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow
import java.util.UUID

internal class GetDailyTaskById(private val repository: DailyRoutineRepository):
    UseCaseInputOutput<UUID, RecurringTask?> {
    override fun execute(param: UUID): Flow<RecurringTask?> = repository.getById(param)
}