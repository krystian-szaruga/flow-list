package com.olr261dn.ui.compose.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.viewmodel.BaseItemViewModel
import com.olr261dn.domain.viewmodel.ReminderController
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.DetailTemplate
import java.util.UUID

abstract class BaseDetailScreenView
<T: Schedulable, VM: BaseItemViewModel<T>, NVM: ReminderController> :
    DetailScreenView, Config<T> {
    protected val confirmLabel: Int = R.string.edit

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(id: UUID, onBackPress: () -> Unit) {
        val item = getItem(id)
        val notificationViewModel = getNotificationViewModel()
        val itemViewModel = getViewModel()

        DetailTemplate(
            title = item.title,
            onBackPress = onBackPress,
            onRemoveConfirm = {
                onRemoveConfirm(itemViewModel, notificationViewModel, item) { onBackPress() }
            },
            popup = { onDismiss ->
                Popup(
                    item = item,
                    onDismiss = { onDismiss() },
                    onEdit = {
                        if (it.isDisabled) {
                            notificationViewModel.cancelNotification(config.getType(), it.id)
                        } else {
                            if (it.scheduledTime != 0L) {
                                notificationViewModel.setNotification(
                                    repeatPattern = config.getRepeatPattern(it),
                                    type = config.getType(),
                                    scheduledTime = it.scheduledTime,
                                    itemId = it.id
                                )
                            }
                        }
                        itemViewModel.updateItem(it)
                    }
                )
            }
        ) {
            DetailsBody(item)
        }
    }

    private fun onRemoveConfirm(
        itemViewModel: VM,
        reminderViewModel: ReminderController,
        item: T,
        onBackPress: () -> Unit
    ) {
        itemViewModel.removeItems(listOf(item))
        reminderViewModel.cancelNotification(
            type = config.getType(),
            itemId = item.id
        )
        onBackPress()
    }

    @Composable
    protected abstract fun getNotificationViewModel(): NVM

    @Composable
    protected abstract fun getItem(id: UUID): T

    @Composable
    protected abstract fun getViewModel(): VM

    @Composable
    protected abstract fun DetailsBody(item: T)

    @Composable
    protected abstract fun Popup(item: T, onDismiss: () -> Unit, onEdit: (T) -> Unit)
}