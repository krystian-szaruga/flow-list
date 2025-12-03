package com.olr261dn.impl.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetDailyTasks(private val repository: DailyRoutineRepository): UseCaseReturnOnly<List<RecurringTask>> {
    override fun execute(): Flow<List<RecurringTask>> = repository.getAll()
}