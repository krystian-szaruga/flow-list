package com.olr261dn.recurring

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.ui.compose.base.BaseScreenView
import com.olr261dn.viewmodel.ReminderViewModel
import com.olr261dn.viewmodel.RecurringTaskItemViewModel

internal object RecurringMain: BaseScreenView
<RecurringTask, RecurringTaskItemViewModel, ReminderViewModel>() {
    override val config = RecurringTaskConfig.reminderConfig

    @Composable
    override fun GetAddPopup(onDismiss: () -> Unit) {
        val viewModel = getViewModel()
        val notificationViewModel = hiltViewModel<ReminderViewModel>()
        AddRecurringTask(initialScheduleCalculator = viewModel.initialScheduleCalculator).Show(
            onDismiss = { onDismiss() },
            onAdd = { onAdd(it, viewModel, notificationViewModel )})
    }

    @Composable
    override fun ItemDisplayer(
        item: RecurringTask,
        isSelected: Boolean,
        onClick: (RecurringTask) -> Unit,
        onLongClick: (RecurringTask) -> Unit
    ) {
        val viewModel = getViewModel()
        ItemDisplayer(
            viewModel = viewModel,
            item = item,
            isSelected = isSelected,
            onClick = { onClick(it) },
            onLongClick = { onLongClick(it) }
        )
    }

    @Composable
    override fun getViewModel(): RecurringTaskItemViewModel = hiltViewModel<RecurringTaskItemViewModel>()

    @Composable
    override fun getReminderViewModel(): ReminderViewModel =
        hiltViewModel<ReminderViewModel>()

}
