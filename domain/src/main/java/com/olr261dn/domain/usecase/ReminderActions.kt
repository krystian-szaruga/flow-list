package com.olr261dn.domain.usecase

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import java.util.UUID

data class ReminderActions (
    val getAll: UseCaseReturnOnly<List<Reminder>>,
    val getIds: UseCaseReturnOnlySuspend<List<Long>>,
    val getById: UseCaseInputOutput<Long, Reminder?>,
    val add: UseCaseInputIdOutput<Reminder, Long>,
    val deleteByItemIdAndType: UseCaseTwoInputParams<UUID, ReminderType>,
    val getByItemIdAndType: UseCaseTwoInputOutput<UUID, ReminderType, Reminder?>
)