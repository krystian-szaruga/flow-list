package com.olr261dn.impl.notification.boot

import android.util.Log
import com.olr261dn.domain.model.Project
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.model.Task
import com.olr261dn.domain.notification.BootCoordinator
import com.olr261dn.domain.notification.ReminderScheduler
import com.olr261dn.domain.usecase.ProjectActions
import com.olr261dn.domain.usecase.RecurringTaskActions
import com.olr261dn.domain.usecase.ReminderActions
import com.olr261dn.domain.usecase.TaskActions
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

internal class BootCoordinatorImpl(
    private val scheduler: ReminderScheduler,
    private val reminderActions: ReminderActions,
    private val dailyActions: RecurringTaskActions,
    private val taskActions: TaskActions,
    private val projectActions: ProjectActions
) : BootCoordinator {
    override suspend fun run(): Boolean {
        val reminders = reminderActions.getAll.execute().firstOrNull().orEmpty()
        val dailyTasks = dailyActions.get.execute().firstOrNull().orEmpty()
        val tasks = taskActions.get.execute().firstOrNull().orEmpty()
        val goals = projectActions.get.execute().firstOrNull().orEmpty()
        reminders.forEach { reminder ->
            val item = getItem(reminder.itemId, reminder.type, dailyTasks, goals, tasks)
            if (item != null && !item.isDisabled) {
                scheduler.schedule(
                    timeInMillis = item.scheduledTime,
                    id = reminder.id,
                    reminderType = reminder.type.name,
                    itemId = reminder.itemId
                )
                Log.i(
                    "BOOT",
                    "Scheduled reminder: id=${reminder.id}, type=${reminder.type.name}," +
                            " time=${reminder.scheduledTime}, itemId=${reminder.itemId}"
                )
            }
        }
        return true
    }

    private fun getItem(
        itemId: UUID,
        type: ReminderType,
        daily: List<RecurringTask>,
        projects: List<Project>,
        tasks: List<Task>
    ): Schedulable? {
        return when (type) {
            ReminderType.DAILY -> filter(daily, itemId)
            ReminderType.TASK -> filter(tasks, itemId)
            ReminderType.GOAL -> filter(projects, itemId)
        }
    }

    private fun <T : Schedulable> filter(items: List<T>, itemId: UUID): Schedulable? =
        items.firstOrNull { it.id == itemId }
}
