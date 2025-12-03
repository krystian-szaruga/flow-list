package com.olr261dn.impl.di

import com.olr261dn.domain.notification.BootCoordinator
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.domain.scheduler.InitialScheduleCalculator
import com.olr261dn.domain.scheduler.NextScheduleCalculator
import com.olr261dn.domain.scheduler.TimeProvider
import com.olr261dn.domain.scheduler.TimeZoneResolver
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.usecase.TaskActions
import com.olr261dn.impl.notification.boot.BootCoordinatorImpl
import com.olr261dn.impl.scheduler.InitialScheduleCalculatorImpl
import com.olr261dn.impl.scheduler.JavaTimeZoneResolver
import com.olr261dn.impl.scheduler.NextScheduleCalculatorImpl
import com.olr261dn.impl.scheduler.SystemTimeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ScheduleModule {
    @Provides
    @Singleton
    fun provideBootCoordinator(
        scheduler: ReminderScheduler,
        reminderActions: ReminderActions,
        dailyActions: RecurringTaskActions,
        taskActions: TaskActions,
        projectActions: ProjectActions
    ): BootCoordinator = BootCoordinatorImpl(
        scheduler, reminderActions, dailyActions, taskActions, projectActions
    )

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider =
        SystemTimeProvider()

    @Provides
    @Singleton
    fun provideTimeZoneResolver(): TimeZoneResolver =
        JavaTimeZoneResolver()

    @Provides
    fun provideInitialScheduleCalculator(
        timeProvider: TimeProvider,
        timeZoneResolver: TimeZoneResolver
    ): InitialScheduleCalculator =
        InitialScheduleCalculatorImpl(timeProvider, timeZoneResolver)

    @Provides
    fun provideNextScheduleCalculator(
        timeProvider: TimeProvider,
        timeZoneResolver: TimeZoneResolver
    ): NextScheduleCalculator =
        NextScheduleCalculatorImpl(timeProvider, timeZoneResolver)
}