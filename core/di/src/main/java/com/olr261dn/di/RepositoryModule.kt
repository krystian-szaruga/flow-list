package com.olr261dn.di

import com.olr261dn.data.database.dao.DailyRoutineDao
import com.olr261dn.data.database.dao.TaskDao
import com.olr261dn.data.repository.DailyRoutineRepositoryImpl
import com.olr261dn.data.repository.TaskRepositoryImpl
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    fun provideDailyRoutineRepository(dailyRoutineDao: DailyRoutineDao): DailyRoutineRepository {
        return DailyRoutineRepositoryImpl(dailyRoutineDao)
    }
}