package com.olr261dn.notification.receiver.utils.notifier

import androidx.work.CoroutineWorker
import com.olr261dn.domain.model.ReminderType
import com.olr261dn.notification.worker.display.DailyDisplayWorker
import com.olr261dn.notification.worker.display.GoalDisplayWorker
import com.olr261dn.notification.worker.display.TaskDisplayWorker

internal object WorkerFactory {
    private val workerMap = mapOf(
        ReminderType.DAILY to DailyDisplayWorker::class.java,
        ReminderType.TASK to TaskDisplayWorker::class.java,
        ReminderType.GOAL to GoalDisplayWorker::class.java

    )
    fun getWorker(type: String): Class<out CoroutineWorker>? =
        workerMap[ReminderType.Companion.fromName(type)]
}