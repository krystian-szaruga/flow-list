package com.olr261dn.impl.di

import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.RecurringTaskNotificationCoordinator
import com.olr261dn.domain.notification.GoalNotification
import com.olr261dn.domain.notification.GoalNotificationCoordinator
import com.olr261dn.domain.notification.TaskNotification
import com.olr261dn.domain.notification.TaskNotificationCoordinator
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.TaskActions
import com.olr261dn.impl.notification.displayer.RecurringTaskNotificationCoordinatorImpl
import com.olr261dn.impl.notification.displayer.GoalNotificationCoordinatorImpl
import com.olr261dn.impl.notification.displayer.TaskNotificationCoordinatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NotificationCoordinatorsModule {
    @Provides
    @Singleton
    fun provideDailyTaskNotificationCoordinator(
        action: RecurringTaskActions,
        displayer: DailyTaskNotification
    ): RecurringTaskNotificationCoordinator = RecurringTaskNotificationCoordinatorImpl(action, displayer)

    @Provides
    @Singleton
    fun provideTaskNotificationCoordinator(
        action: TaskActions,
        goalAction: ProjectActions,
        displayer: TaskNotification
    ): TaskNotificationCoordinator = TaskNotificationCoordinatorImpl(action, goalAction, displayer)

    @Provides
    @Singleton
    fun provideGoalNotificationCoordinator(
        action: ProjectActions,
        displayer: GoalNotification
    ): GoalNotificationCoordinator = GoalNotificationCoordinatorImpl(action, displayer)
}