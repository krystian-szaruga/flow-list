package com.olr261dn.task

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.popup.AddItemPopup
import com.olr261dn.ui.utils.getTask
import java.util.UUID

class AddTask(
    @field:StringRes private val confirmLabel: Int = R.string.save,
    private val id: UUID? = null,
    private val title: String = "",
    private val goalId: UUID? = null,
    private val description: String = "",
    private val scheduledDateTime: Long = 0L,
//    private val disableDateTimePicker: Boolean = false
) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Show(onAdd: (Task) -> Unit, onDismiss: () -> Unit) {
//        val disableSchedule = rememberSaveable { mutableStateOf(true) }
//        val dateTimePickerFlag = disableDateTimePicker && disableSchedule.value

        AddItemPopup(
            confirmLabel = confirmLabel,
            titleLabel = R.string.task_title,
            id = this.id,
            title = this.title,
            description = this.description,
            scheduledDateTime = this.scheduledDateTime,
            additionalContent = {
//                if (disableDateTimePicker) {
//                    Row(
//                        verticalAlignment = Alignment.Companion.CenterVertically
//                    ) {
//                        Checkbox(
//                            checked = disableSchedule.value,
//                            onCheckedChange = { disableSchedule.value = it }
//                        )
//                        Text(stringResource(R.string.disable_deadline), fontSize = 20.nonScaledSp)
//                    }
//                }
            },
            onDismiss = onDismiss,
        ) { id, title, description, time, repeatPattern ->
            onAdd(getTask(id, title, description, this.goalId, time, repeatPattern))
        }
    }
}