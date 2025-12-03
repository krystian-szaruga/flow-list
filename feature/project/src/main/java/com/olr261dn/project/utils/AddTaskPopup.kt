package com.olr261dn.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.olr261dn.domain.model.Task
import com.olr261dn.ui.compose.common.popup.AddItemPopup
import com.olr261dn.ui.utils.getTask

@Composable
internal fun AddTaskPopup(
    maxScheduledTime: Long? = null,
    onDismiss: () -> Unit,
    onAdd: (Task) -> Unit
) {
    key("task_popup") {
        AddItemPopup(
            showSchedulingOptions = true,
            maxScheduledTime = maxScheduledTime,
            onDismiss = { onDismiss() },
            onAdd = { id, title, description, time, repeatPattern ->
                onAdd(getTask(id, title, description, null, time, repeatPattern))
            }
        )
    }
}