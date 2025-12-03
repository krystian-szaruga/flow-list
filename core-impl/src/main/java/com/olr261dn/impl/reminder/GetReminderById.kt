package com.olr261dn.impl.reminder

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseInputOutput
import kotlinx.coroutines.flow.Flow

internal class GetReminderById(private val repository: ReminderRepository
): UseCaseInputOutput<Long, Reminder?> {
    override fun execute(param: Long): Flow<Reminder?> = repository.getById(param)
}