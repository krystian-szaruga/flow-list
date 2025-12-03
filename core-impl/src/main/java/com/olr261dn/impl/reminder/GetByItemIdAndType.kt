package com.olr261dn.impl.reminder

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseTwoInputOutput
import java.util.UUID

internal class GetByItemIdAndType(private val repository: ReminderRepository):
    UseCaseTwoInputOutput<UUID, ReminderType, Reminder?>
{
    override suspend fun execute(param1: UUID, param2: ReminderType): Reminder? =
        repository.getByIdAndType(param1, param2)
}