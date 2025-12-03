package com.olr261dn.data.di

import android.content.Context
import com.olr261dn.data.database.dao.CompletionHistoryDao
import com.olr261dn.data.database.dao.DailyTaskDao
import com.olr261dn.data.database.dao.GoalDao
import com.olr261dn.data.database.dao.ReminderDao
import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.repository.CompletionHistoryRepositoryImpl
import com.olr261dn.data.repository.DailyRoutineRepositoryImpl
import com.olr261dn.data.repository.GoalRepositoryImpl
import com.olr261dn.data.repository.ReminderRepositoryImpl
import com.olr261dn.data.repository.TaskRepositoryImpl
import com.olr261dn.data.repository.UserPreferencesRepositoryImpl
import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideDailyRoutineRepository(dao: DailyTaskDao): DailyRoutineRepository =
        DailyRoutineRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context):
            UserPreferencesRepository = UserPreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideCompletionHistoryRepository(dao: CompletionHistoryDao):
            CompletionHistoryRepository = CompletionHistoryRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideReminderRepository(dao: ReminderDao): ReminderRepository =
        ReminderRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao, taskDao: TaskDao): GoalRepository =
        GoalRepositoryImpl(goalDao = goalDao, taskDao = taskDao)
}