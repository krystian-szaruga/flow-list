package com.olr261dn.impl.reminder

import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnlySuspend

internal class GetReminderIds(private val repository: ReminderRepository
): UseCaseReturnOnlySuspend<List<Long>> {
    override suspend fun execute(): List<Long> = repository.getIds()
}