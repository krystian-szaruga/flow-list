package com.olr261dn.notification

import android.content.Context
import com.olr261dn.domain.notification.DailyTaskNotification
import com.olr261dn.domain.notification.GoalNotification
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.domain.notification.Rescheduler
import com.olr261dn.domain.notification.TaskNotification
import com.olr261dn.notification.manager.GoalNotificationImpl
import com.olr261dn.notification.manager.RecurringTaskNotificationImpl
import com.olr261dn.notification.manager.TaskNotificationImpl
import com.olr261dn.notification.rescheduler.ReschedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NotificationModule {

    @Provides
    @Singleton
    fun provideDailyTaskNotification(@ApplicationContext context: Context):
            DailyTaskNotification = RecurringTaskNotificationImpl(context)

    @Provides
    @Singleton
    fun provideTaskNotification(@ApplicationContext context: Context):
            TaskNotification = TaskNotificationImpl(context)

    @Provides
    @Singleton
    fun provideGoalNotification(@ApplicationContext context: Context):
            GoalNotification = GoalNotificationImpl(context)


    @Provides
    @Singleton
    fun provideReminderScheduler(@ApplicationContext context: Context): ReminderScheduler =
        ReminderSchedulerImpl(context)


    @Provides
    @Singleton
    fun provideRescheduler(scheduler: ReminderScheduler): Rescheduler =
        ReschedulerImpl(scheduler)
}
