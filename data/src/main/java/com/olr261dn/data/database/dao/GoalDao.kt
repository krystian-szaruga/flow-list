package com.olr261dn.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.olr261dn.data.database.entity.GoalWithTasksEntity
import com.olr261dn.data.database.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface GoalDao: BaseDao<ProjectEntity, UUID> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: ProjectEntity)

    @Update
    override suspend fun update(item: ProjectEntity)

    @Update
    suspend fun upsert(item: ProjectEntity)

    @Query("DELETE FROM ProjectEntity WHERE id IN (:ids)")
    override suspend fun delete(ids: List<UUID>)

    @Query("SELECT * FROM ProjectEntity")
    override fun getAll(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM ProjectEntity WHERE id = :id")
    override fun getById(id: UUID): Flow<ProjectEntity?>

    @Query("SELECT id FROM ProjectEntity")
    override fun getIds(): Flow<List<UUID>>

    // Additional GoalDao-specific methods
    @Transaction
    @Query("SELECT * FROM ProjectEntity WHERE id = :goalId")
    fun getGoalWithTasks(goalId: UUID): Flow<GoalWithTasksEntity?>

    @Transaction
    @Query("SELECT * FROM ProjectEntity")
    fun getAllGoalsWithTasks(): Flow<List<GoalWithTasksEntity>>
}