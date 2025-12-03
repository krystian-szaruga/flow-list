package com.olr261dn.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.olr261dn.data.database.entity.CompletionHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface CompletionHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: CompletionHistoryEntity)

    @Update
    suspend fun update(history: CompletionHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(historyList: List<CompletionHistoryEntity>)

    @Query("SELECT * FROM CompletionHistoryEntity")
    fun getAll(): Flow<List<CompletionHistoryEntity>>

    @Query("SELECT * FROM CompletionHistoryEntity WHERE recurringTaskId = :routineId ORDER BY date DESC")
    fun getHistoryForDailyRoutine(routineId: UUID): Flow<List<CompletionHistoryEntity>>

    @Query("SELECT * FROM CompletionHistoryEntity WHERE taskId = :taskId ORDER BY date DESC")
    fun getHistoryForTask(taskId: UUID): Flow<List<CompletionHistoryEntity>>

    @Query("SELECT * FROM CompletionHistoryEntity WHERE projectId = :goalId ORDER BY date DESC")
    fun getHistoryForStandaloneGoal(goalId: UUID): Flow<List<CompletionHistoryEntity>>

    @Query("""
        SELECT * FROM CompletionHistoryEntity 
        WHERE date BETWEEN :startDate AND :endDate 
        ORDER BY date ASC
    """)
    fun getHistoryInRange(startDate: Long, endDate: Long): Flow<List<CompletionHistoryEntity>>

    @Query(
        """
        SELECT COUNT(*) FROM CompletionHistoryEntity 
        WHERE isCompleted = 1 AND 
        (recurringTaskId = :id OR taskId = :id OR projectId = :id)
    """
    )
    fun countCompletionsForAnyType(id: UUID): Flow<Int>
}