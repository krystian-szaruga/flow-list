package com.olr261dn.domain.repository

import com.olr261dn.domain.model.CompletionHistory
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CompletionHistoryRepository: BaseRepository {
    suspend fun insert(completion: CompletionHistory)
    suspend fun update(completion: CompletionHistory)
    fun getAll(): Flow<List<CompletionHistory>>
    suspend fun insertAll(completions: List<CompletionHistory>)
    fun getHistoryForDailyRoutine(id: UUID): Flow<List<CompletionHistory>>
    fun getHistoryForTask(id: UUID): Flow<List<CompletionHistory>>
    fun getHistoryForStandaloneGoal(id: UUID): Flow<List<CompletionHistory>>
    fun getHistoryInRange(startDate: Long, endDate: Long): Flow<List<CompletionHistory>>
    fun countCompletionsForAnyType(id: UUID): Flow<Int>
}