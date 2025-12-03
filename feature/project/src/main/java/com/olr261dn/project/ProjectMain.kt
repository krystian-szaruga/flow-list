package com.olr261dn.project

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import com.olr261dn.ui.compose.base.BaseScreenView
import com.olr261dn.ui.compose.base.ReminderConfig
import com.olr261dn.viewmodel.GoalItemViewModel
import com.olr261dn.viewmodel.ReminderViewModel

internal object ProjectMain: BaseScreenView<Project, GoalItemViewModel, ReminderViewModel>() {
    @Composable
    override fun getViewModel(): GoalItemViewModel = hiltViewModel<GoalItemViewModel>()

    @Composable
    override fun getReminderViewModel(): ReminderViewModel = hiltViewModel<ReminderViewModel>()

    @Composable
    override fun GetAddPopup(onDismiss: () -> Unit) {
        val viewModel = getViewModel()
        val notificationViewModel = hiltViewModel<ReminderViewModel>()
        AddProject().Show(onDismiss = { onDismiss() }, onAdd = { onAdd(it, viewModel, notificationViewModel)})
    }
    @Composable
    override fun ItemDisplayer(
        item: Project,
        isSelected: Boolean,
        onClick: (Project) -> Unit,
        onLongClick: (Project) -> Unit,
    ) {
        GoalCard(
            project = item,
            isSelected = isSelected,
            onClick = { onClick(item) },
            onLongClick = { onLongClick(item) }
        )
    }

    private fun onAdd(
        item: GoalWithTasks,
        viewModel: GoalItemViewModel,
        notificationViewModel: ReminderViewModel
    ) {
        viewModel.addGoalWithTasks(item)
        notificationViewModel.setNotificationsForGoal(item)
    }

    override val config: ReminderConfig<Project> = ProjectReminderConfig
}