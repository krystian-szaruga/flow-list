package com.olr261dn.data.repository

import com.olr261dn.data.database.dao.ReminderDao
import com.olr261dn.data.mapper.toDomain
import com.olr261dn.data.mapper.toEntity
import com.olr261dn.domain.model.Reminder
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

internal class ReminderRepositoryImpl @Inject constructor (private val dao: ReminderDao):
    ReminderRepository {
    override fun getAll(): Flow<List<Reminder>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getIds(): List<Long> = dao.getIds()

    override suspend fun getByIdAndType(id: UUID, type: ReminderType): Reminder? =
        dao.getByItemIdAndType(id, type.name)?.toDomain()

    override fun getById(id: Long): Flow<Reminder?> = dao.getById(id).map { it?.toDomain() }

    override suspend fun add(reminder: Reminder): Long = dao.insert(reminder.toEntity())

    override suspend fun deleteByItemIdAndType(id: UUID, type: ReminderType) =
        dao.deleteByItemIdAndType(id, type.name)
}
