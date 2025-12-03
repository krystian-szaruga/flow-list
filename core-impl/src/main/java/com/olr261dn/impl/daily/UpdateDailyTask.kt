package com.olr261dn.impl.daily

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.usecase.UseCaseInput

internal class UpdateDailyTask(private val repository: DailyRoutineRepository): UseCaseInput<RecurringTask> {
    override suspend fun execute(param: RecurringTask) = repository.update(param)
}