package com.olr261dn.project.utils

import androidx.compose.runtime.Composable
import com.olr261dn.domain.model.Task
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.popup.AddItemPopup
import com.olr261dn.ui.utils.getTask


@Composable
fun EditTaskPopup(
    task: Task,
    maxScheduledTime: Long? = null,
    onDismiss: () -> Unit,
    onEdit: (Task) -> Unit
) {
    AddItemPopup(
        showSchedulingOptions = true,
        confirmLabel = R.string.edit,
        id = task.id,
        title = task.title,
        description = task.description,
        scheduledDateTime = task.scheduledTime,
        repeatPattern = task.repeatPattern,
        maxScheduledTime = maxScheduledTime,
        onDismiss = { onDismiss() },
        onAdd = { id, title, description, time, repeatPattern ->
            onEdit(getTask(id, title, description, null, time, repeatPattern))
        }
    )
}