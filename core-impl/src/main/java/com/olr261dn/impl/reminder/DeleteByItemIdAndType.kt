package com.olr261dn.impl.reminder

import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseTwoInputParams
import java.util.UUID

internal class DeleteByItemIdAndType(private val repository: ReminderRepository):
    UseCaseTwoInputParams<UUID, ReminderType>
{
    override suspend fun execute(param1: UUID, param2: ReminderType) =
        repository.deleteByItemIdAndType(param1, param2)

}