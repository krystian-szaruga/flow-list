package com.olr261dn.impl.reminder

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseReturnOnly
import kotlinx.coroutines.flow.Flow

internal class GetAllReminders(private val repository: ReminderRepository
): UseCaseReturnOnly<List<Reminder>> {
    override fun execute(): Flow<List<Reminder>> = repository.getAll()
}