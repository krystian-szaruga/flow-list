package com.olr261dn.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olr261dn.data.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
internal interface ReminderDao {
    @Query("SELECT * FROM reminders")
    fun getAll(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE itemId = :itemId AND type = :itemType LIMIT 1")
    suspend fun getByItemIdAndType(itemId: UUID, itemType: String): ReminderEntity?

    @Query("SELECT id FROM reminders")
    suspend fun getIds(): List<Long>

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flow<ReminderEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity): Long

    @Query("DELETE FROM reminders WHERE itemId = :itemId AND type = :itemType")
    suspend fun deleteByItemIdAndType(itemId: UUID, itemType: String)
}
