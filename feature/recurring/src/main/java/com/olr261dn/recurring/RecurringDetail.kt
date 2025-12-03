package com.olr261dn.recurring

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.ui.compose.base.BaseDetailScreenView
import com.olr261dn.ui.utils.toHourMinute
import com.olr261dn.viewmodel.RecurringTaskItemViewModel
import com.olr261dn.viewmodel.ReminderViewModel
import java.util.UUID

internal object RecurringDetail:
    BaseDetailScreenView<RecurringTask, RecurringTaskItemViewModel, ReminderViewModel>() {
    override val config = RecurringTaskConfig.reminderConfig

    @Composable
    override fun getNotificationViewModel(): ReminderViewModel = hiltViewModel<ReminderViewModel>()

    @Composable
    override fun getItem(id: UUID): RecurringTask =
        getViewModel().items.collectAsState().value.firstOrNull { it.id == id } ?: RecurringTask(UUID.randomUUID())

    @Composable
    override fun getViewModel(): RecurringTaskItemViewModel {
        return hiltViewModel<RecurringTaskItemViewModel>()
    }

    @Composable
    override fun DetailsBody(item: RecurringTask) {
        DetailsContent(item)
    }

    @Composable
    override fun Popup(item: RecurringTask, onDismiss: () -> Unit, onEdit: (RecurringTask) -> Unit) {
        val (hour, minute) = item.scheduledTime.toHourMinute()
        val viewModel = getViewModel()
        AddRecurringTask(
            confirmLabel = this.confirmLabel,
            initialScheduleCalculator = viewModel.initialScheduleCalculator,
            id = item.id,
            title = item.title,
            description = item.description,
            isDisabled = item.isDisabled,
            scheduleType = item.scheduleType,
            hour = hour,
            minute = minute,
            customDays = item.customDays
        ).Show(
            onDismiss = { onDismiss() },
            onAdd = { onEdit(it) }
        )
    }
}
