package com.olr261dn.ui.compose.common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.olr261dn.resources.R

@Composable
fun DatePickerWrapper(
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit
) {
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { PickerDialogButton(value = R.string.ok) { confirmButton() } },
            dismissButton = { PickerDialogButton(value = R.string.cancel) { dismissButton() } }
        ) {
            DatePicker(state = datePickerState)
        }

    }
}


@Composable
private fun PickerDialogButton(value: Int, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(stringResource(value))
    }
}