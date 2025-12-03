package com.olr261dn.impl.di

import com.olr261dn.domain.repository.CompletionHistoryRepository
import com.olr261dn.domain.repository.DailyRoutineRepository
import com.olr261dn.domain.repository.GoalRepository
import com.olr261dn.domain.repository.ReminderRepository
import com.olr261dn.domain.repository.TaskRepository
import com.olr261dn.domain.usecase.CompletionHistoryActions
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.usecase.TaskActions
import com.olr261dn.impl.daily.AddDailyTask
import com.olr261dn.impl.daily.GetDailyTaskById
import com.olr261dn.impl.daily.GetDailyTasks
import com.olr261dn.impl.daily.RemoveDailyTask
import com.olr261dn.impl.daily.UpdateDailyTask
import com.olr261dn.impl.goal.AddGoalWithTasks
import com.olr261dn.impl.goal.AddGoals
import com.olr261dn.impl.goal.GetAllGoals
import com.olr261dn.impl.goal.GetAllGoalsWithTasks
import com.olr261dn.impl.goal.GetGoalById
import com.olr261dn.impl.goal.GetGoalIds
import com.olr261dn.impl.goal.GetGoalWithTasks
import com.olr261dn.impl.goal.RemoveGoals
import com.olr261dn.impl.goal.UpdateGoal
import com.olr261dn.impl.goal.UpdateGoalWithTasks
import com.olr261dn.impl.history.AddAllCompletionHistories
import com.olr261dn.impl.history.AddCompletionHistory
import com.olr261dn.impl.history.CountCompletionsForAnyType
import com.olr261dn.impl.history.GetAllCompletionHistory
import com.olr261dn.impl.history.GetHistoryForDaily
import com.olr261dn.impl.history.GetHistoryForStandaloneGoal
import com.olr261dn.impl.history.GetHistoryForTask
import com.olr261dn.impl.history.GetHistoryInRange
import com.olr261dn.impl.history.UpdateCompletionHistory
import com.olr261dn.impl.reminder.AddReminder
import com.olr261dn.impl.reminder.DeleteByItemIdAndType
import com.olr261dn.impl.reminder.GetAllReminders
import com.olr261dn.impl.reminder.GetByItemIdAndType
import com.olr261dn.impl.reminder.GetReminderById
import com.olr261dn.impl.reminder.GetReminderIds
import com.olr261dn.impl.task.AddTask
import com.olr261dn.impl.task.GetStandaloneTasks
import com.olr261dn.impl.task.GetTaskById
import com.olr261dn.impl.task.GetTasks
import com.olr261dn.impl.task.RemoveTask
import com.olr261dn.impl.task.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DomainActionsModule {
    @Provides
    @Singleton
    fun provideRecurringTaskActions(repository: DailyRoutineRepository): RecurringTaskActions =
        RecurringTaskActions(
            add = AddDailyTask(repository),
            get = GetDailyTasks(repository),
            getById = GetDailyTaskById(repository),
            remove = RemoveDailyTask(repository),
            update = UpdateDailyTask(repository)
        )

    @Provides
    @Singleton
    fun provideReminderUseCases(repository: ReminderRepository): ReminderActions =
        ReminderActions(
            getAll = GetAllReminders(repository),
            getIds = GetReminderIds(repository),
            getById = GetReminderById(repository),
            add = AddReminder(repository),
            deleteByItemIdAndType = DeleteByItemIdAndType(repository),
            getByItemIdAndType = GetByItemIdAndType(repository)
        )

    @Provides
    @Singleton
    fun provideCompletionHistoryActions(
        repository: CompletionHistoryRepository
    ): CompletionHistoryActions =
        CompletionHistoryActions(
            add = AddCompletionHistory(repository),
            update = UpdateCompletionHistory(repository),
            getAll = GetAllCompletionHistory(repository),
            addAll = AddAllCompletionHistories(repository),
            getForDaily = GetHistoryForDaily(repository),
            getForTask = GetHistoryForTask(repository),
            getForStandaloneGoal = GetHistoryForStandaloneGoal(repository),
            getInRange = GetHistoryInRange(repository),
            countCompletionsForAnyType = CountCompletionsForAnyType(repository)
        )

    @Provides
    @Singleton
    fun provideTaskActions(repository: TaskRepository): TaskActions {
        return TaskActions(
            add = AddTask(repository),
            getStandaloneTasks = GetStandaloneTasks(repository),
            get = GetTasks(repository),
            getById = GetTaskById(repository),
            remove = RemoveTask(repository),
            update = UpdateTask(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGoalActions(repository: GoalRepository): ProjectActions {
        return ProjectActions(
            add = AddGoals(repository),
            get = GetAllGoals(repository),
            getById = GetGoalById(repository),
            remove = RemoveGoals(repository),
            update = UpdateGoal(repository),
            getWithTasks = GetGoalWithTasks(repository),
            getAllWithTasks = GetAllGoalsWithTasks(repository),
            addWithTasks = AddGoalWithTasks(repository),
            updateGoalWithTasks = UpdateGoalWithTasks(repository),
            getIds = GetGoalIds(repository)
        )
    }
}
