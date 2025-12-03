package com.olr261dn.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.olr261dn.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface TaskDao : BaseDao<TaskEntity, UUID> {
    @Insert
    override suspend fun insert(item: TaskEntity)

    @Update
    override suspend fun update(item: TaskEntity)

    @Upsert
    suspend fun upsert(item: TaskEntity)

    @Query("SELECT * FROM TaskEntity")
    override fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE goalId IS NULL")
    fun getStandaloneTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    override fun getById(id: UUID): Flow<TaskEntity?>

    @Query("SELECT id FROM TaskEntity")
    override  fun getIds(): Flow<List<UUID>>

    @Query("DELETE FROM TaskEntity WHERE id IN (:ids)")
    override suspend fun delete(ids: List<UUID>)
}