package com.olr261dn.domain.repository

import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ReminderRepository {
    fun getAll(): Flow<List<Reminder>>
    suspend fun getIds(): List<Long>
    suspend fun getByIdAndType(id: UUID, type: ReminderType): Reminder?
    fun getById(id: Long): Flow<Reminder?>
    suspend fun add(reminder: Reminder): Long
    suspend fun deleteByItemIdAndType(id: UUID, type: ReminderType)
}