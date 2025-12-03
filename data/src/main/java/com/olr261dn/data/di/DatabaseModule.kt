package com.olr261dn.data.di

import android.content.Context
import androidx.room.Room
import com.olr261dn.data.database.AppDatabase
import com.olr261dn.data.database.dao.CompletionHistoryDao
import com.olr261dn.data.database.dao.DailyTaskDao
import com.olr261dn.data.database.dao.GoalDao
import com.olr261dn.data.database.dao.ReminderDao
import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.di.utils.EncryptionService
import com.olr261dn.data.di.utils.MIGRATION_2_3
import com.olr261dn.data.di.utils.MIGRATION_3_4
import com.olr261dn.data.di.utils.MIGRATION_4_5
import com.olr261dn.data.di.utils.MIGRATION_5_6
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
//import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            EncryptionService.DATABASE_NAME
        )
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .addMigrations(MIGRATION_5_6)
//            .openHelperFactory(SupportFactory(EncryptionService.getPassphrase(context)))
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideDailyRoutineDao(db: AppDatabase): DailyTaskDao = db.dailyRoutineDao()

    @Provides
    fun provideCompletionHistoryDao(db: AppDatabase): CompletionHistoryDao =
        db.completionHistoryDao()

    @Provides
    fun provideReminderDao(db: AppDatabase): ReminderDao = db.reminderDao()
}
