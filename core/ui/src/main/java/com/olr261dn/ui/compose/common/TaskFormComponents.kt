package com.olr261dn.ui.compose.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.formatHourMinute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerButton(
    timePickerState: TimePickerState,
    contentDescription: String,
    isError: Boolean = false,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = customTheme().background,
            contentColor = customTheme().onBackground
        ),
        border = if (isError) BorderStroke(2.dp, MaterialTheme.colorScheme.error) else null
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_access_time_filled_24),
                contentDescription = contentDescription,
                tint = if (isError) MaterialTheme.colorScheme.error else LocalContentColor.current
            )
            Text(
                text = formatHourMinute(timePickerState.hour, timePickerState.minute),
                color = if (isError) MaterialTheme.colorScheme.error else LocalContentColor.current
            )
        }
    }
}

@Composable
fun TaskFormFields(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    titleLabel: String,
    isTitleError: Boolean
) {
    TitleField(
        isError = isTitleError,
        label = titleLabel,
        value = title
    ) {
        onTitleChange(it)
    }
    DescriptionField(description) { onDescriptionChange(it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogWrapper(
    showTimePicker: Boolean,
    timePickerState: TimePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showTimePicker) {
        TimePickerDialog(
            timePickerState = timePickerState,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}