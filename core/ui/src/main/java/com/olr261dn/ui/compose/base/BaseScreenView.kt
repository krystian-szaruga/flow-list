package com.olr261dn.ui.compose.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.olr261dn.domain.model.Schedulable
import com.olr261dn.domain.viewmodel.BaseItemViewModel
import com.olr261dn.domain.viewmodel.ReminderController
import com.olr261dn.ui.compose.internal.screenBody.ScreenBody
import com.olr261dn.ui.utils.toggle
import java.util.UUID

abstract class BaseScreenView<
    T : Schedulable,
    VM : BaseItemViewModel<T>,
    NVM : ReminderController
> : ScreenView, Config<T> {
    @Composable
    override fun Content(
        title: String,
        screenTemplate: Template,
        onDetailClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    ) {
        val notificationViewModel = getReminderViewModel()
        val viewModel = getViewModel()
        val addFlag = rememberSaveable { mutableStateOf(false) }

        screenTemplate.Screen(
            title = title,
            onTap = { onAddPopupPress(addFlag) },
            onStatistics = { onStatistic() }
        ) {
            if (addFlag.value) {
                GetAddPopup(
                    onDismiss = { onAddPopupPress(addFlag) }
                )
            }
            ScreenBody(
                viewModel = viewModel,
                content = { item, isSelected, onClick, onLongClick ->
                    ItemDisplayer(
                        item = item,
                        isSelected = isSelected,
                        onClick = { onClick(it) },
                        onLongClick = { onLongClick(it) },
                    )
                },
                onDelete = { items ->
                    viewModel.removeItems(items)
                    items.forEach {
                        notificationViewModel.cancelNotification(
                            type = config.getType(),
                            itemId = it.id
                        )
                    }
                },
                onDetail = { onDetailClick(it.id) }
            )
        }
    }

    protected fun onAdd(
        item: T,
        viewModel: VM,
        reminderViewModel: ReminderController,
    ) {
        viewModel.addItem(item)
        if (item.scheduledTime != 0L) {
            reminderViewModel.setNotification(
                repeatPattern = config.getRepeatPattern(item),
                type = config.getType(),
                scheduledTime = item.scheduledTime,
                itemId = item.id
            )
        }
    }

    @Composable
    protected abstract fun getViewModel(): VM

    @Composable
    protected abstract fun getReminderViewModel(): NVM

    @Composable
    protected abstract fun GetAddPopup(onDismiss: () -> Unit)

    private fun onAddPopupPress(addDailyTask: MutableState<Boolean>) = addDailyTask.toggle()

    @Composable
    protected abstract fun ItemDisplayer(
        item: T, isSelected: Boolean, onClick: (T) -> Unit, onLongClick: (T) -> Unit
    )
}
