package com.olr261dn.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olr261dn.data.database.entity.RecurringTaskEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface DailyTaskDao : BaseDao<RecurringTaskEntity, UUID> {
    @Insert
    override suspend fun insert(item: RecurringTaskEntity)

    @Update
    override suspend fun update(item: RecurringTaskEntity)

    @Query("SELECT * FROM RecurringTaskEntity")
    override fun getAll(): Flow<List<RecurringTaskEntity>>

    @Query("SELECT * FROM RecurringTaskEntity WHERE id = :id")
    override fun getById(id: UUID): Flow<RecurringTaskEntity?>

    @Query("SELECT id FROM RecurringTaskEntity")
    override fun getIds(): Flow<List<UUID>>

    @Query("DELETE FROM RecurringTaskEntity WHERE id IN (:ids)")
    override suspend fun delete(ids: List<UUID>)
}
