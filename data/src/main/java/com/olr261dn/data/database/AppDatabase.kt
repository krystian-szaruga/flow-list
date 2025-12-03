package com.olr261dn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olr261dn.data.database.dao.CompletionHistoryDao
import com.olr261dn.data.database.dao.DailyTaskDao
import com.olr261dn.data.database.dao.GoalDao
import com.olr261dn.data.database.dao.ReminderDao
import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.database.entity.CompletionHistoryEntity
import com.olr261dn.data.database.entity.ProjectEntity
import com.olr261dn.data.database.entity.RecurringTaskEntity
import com.olr261dn.data.database.entity.ReminderEntity
import com.olr261dn.data.database.entity.TaskEntity

@Database(
    entities = [
        ProjectEntity::class, TaskEntity::class, RecurringTaskEntity::class,
        CompletionHistoryEntity::class, ReminderEntity::class
    ],
    version = 6,
    exportSchema = false
)
internal abstract class AppDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun dailyRoutineDao(): DailyTaskDao
    abstract fun completionHistoryDao(): CompletionHistoryDao
    abstract fun reminderDao(): ReminderDao
}