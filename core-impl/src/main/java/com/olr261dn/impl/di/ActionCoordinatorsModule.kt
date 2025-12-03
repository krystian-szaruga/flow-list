package com.olr261dn.impl.di

import com.olr261dn.domain.notification.DelayRecurringCoordinator
import com.olr261dn.domain.notification.DelayTaskCoordinator
import com.olr261dn.domain.notification.DoneDailyCoordinator
import com.olr261dn.domain.notification.DoneGoalCoordinator
import com.olr261dn.domain.notification.DoneTaskCoordinator
import com.olr261dn.domain.notification.SkipDailyCoordinator
import com.olr261dn.domain.notification.SkipGoalCoordinator
import com.olr261dn.domain.notification.SkipTaskCoordinator
import com.olr261dn.impl.notification.action.daily.DelayRecurringCoordinatorImpl
import com.olr261dn.impl.notification.action.daily.DoneDailyCoordinatorImpl
import com.olr261dn.impl.notification.action.daily.RecurringTaskCoordinatorFactory
import com.olr261dn.impl.notification.action.daily.SkipDailyCoordinatorImpl
import com.olr261dn.impl.notification.action.goal.DoneGoalCoordinatorImpl
import com.olr261dn.impl.notification.action.goal.ProjectCoordinatorFactory
import com.olr261dn.impl.notification.action.goal.SkipGoalCoordinatorImpl
import com.olr261dn.impl.notification.action.task.DelayTaskCoordinatorImpl
import com.olr261dn.impl.notification.action.task.DoneTaskCoordinatorImpl
import com.olr261dn.impl.notification.action.task.SkipTaskCoordinatorImpl
import com.olr261dn.impl.notification.action.task.TaskCoordinatorFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ActionCoordinatorsModule {
    @Provides
    @Singleton
    fun provideDoneDailyCoordinator(factory: RecurringTaskCoordinatorFactory): DoneDailyCoordinator =
        DoneDailyCoordinatorImpl(factory.create(isCompleted = true))

    @Provides
    @Singleton
    fun provideSkipDailyCoordinator(factory: RecurringTaskCoordinatorFactory): SkipDailyCoordinator =
        SkipDailyCoordinatorImpl(factory.create(isCompleted = false))

    @Provides
    @Singleton
    fun provideDelayDailyCoordinator(factory: RecurringTaskCoordinatorFactory): DelayRecurringCoordinator =
        DelayRecurringCoordinatorImpl(factory.createSimple())

    @Provides
    @Singleton
    fun provideDoneTaskCoordinator(factory: TaskCoordinatorFactory): DoneTaskCoordinator =
        DoneTaskCoordinatorImpl(factory.create(isCompleted = true))

    @Provides
    @Singleton
    fun provideSkipTaskCoordinator(factory: TaskCoordinatorFactory): SkipTaskCoordinator =
        SkipTaskCoordinatorImpl(factory.create(isCompleted = false))

    @Provides
    @Singleton
    fun provideDelayTaskCoordinator(factory: TaskCoordinatorFactory): DelayTaskCoordinator =
        DelayTaskCoordinatorImpl(factory.createSimple())

    @Provides
    @Singleton
    fun provideDoneGoalCoordinator(factory: ProjectCoordinatorFactory): DoneGoalCoordinator =
        DoneGoalCoordinatorImpl(factory.create(isCompleted = true))

    @Provides
    @Singleton
    fun provideSkipGoalCoordinator(factory: ProjectCoordinatorFactory): SkipGoalCoordinator =
        SkipGoalCoordinatorImpl(factory.create(isCompleted = false))
}