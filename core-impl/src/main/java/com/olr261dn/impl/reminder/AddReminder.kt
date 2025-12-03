package com.olr261dn.impl.reminder

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.usecase.UseCaseInputIdOutput

internal class AddReminder(
    private val repository: ReminderRepository
) : UseCaseInputIdOutput<Reminder, Long> {

    override suspend fun execute(param: Reminder): Long {
        val nextId = findNextAvailableId()
        val reminderWithId = param.copy(id = nextId)
        return repository.add(reminderWithId)
    }

    private suspend fun findNextAvailableId(): Long {
        val existingIds = repository.getIds().sorted()
        if (existingIds.isEmpty()) return 1L
        existingIds.forEachIndexed { index, id ->
            val expectedId = index + 1L
            if (id != expectedId) {
                return expectedId
            }
        }
        return existingIds.size + 1L
    }
}