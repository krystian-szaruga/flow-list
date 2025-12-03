package com.olr261dn.data.repository

import com.olr261dn.data.database.dao.CompletionHistoryDao
import com.olr261dn.data.mapper.toDomain
import com.olr261dn.data.mapper.toEntity
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.repository.CompletionHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

internal class CompletionHistoryRepositoryImpl @Inject constructor(
    private val completionHistoryDao: CompletionHistoryDao
) : CompletionHistoryRepository {

    override suspend fun insert(completion: CompletionHistory) {
        completionHistoryDao.insert(completion.toEntity())
    }

    override suspend fun update(completion: CompletionHistory) {
        completionHistoryDao.update(completion.toEntity())
    }

    override fun getAll(): Flow<List<CompletionHistory>> =
        completionHistoryDao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun insertAll(completions: List<CompletionHistory>) {
        completionHistoryDao.insertAll(completions.map { it.toEntity() })
    }

    override fun getHistoryForDailyRoutine(id: UUID): Flow<List<CompletionHistory>> {
        return completionHistoryDao
            .getHistoryForDailyRoutine(id)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getHistoryForTask(id: UUID): Flow<List<CompletionHistory>> {
        return completionHistoryDao
            .getHistoryForTask(id)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getHistoryForStandaloneGoal(id: UUID): Flow<List<CompletionHistory>> {
        return completionHistoryDao
            .getHistoryForStandaloneGoal(id)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getHistoryInRange(startDate: Long, endDate: Long): Flow<List<CompletionHistory>> {
        return completionHistoryDao
            .getHistoryInRange(startDate, endDate)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun countCompletionsForAnyType(id: UUID): Flow<Int> {
        return completionHistoryDao.countCompletionsForAnyType(id)
    }

    override fun getIds(): Flow<List<UUID>> {
        return completionHistoryDao.getAll().map { list -> list.map { it.id } }
    }
}