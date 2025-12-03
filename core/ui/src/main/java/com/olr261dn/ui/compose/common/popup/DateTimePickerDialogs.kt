package com.olr261dn.ui.compose.common.popup

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import com.olr261dn.ui.compose.common.DatePickerWrapper
import com.olr261dn.ui.compose.common.TimePickerDialogWrapper
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateTimePickerDialogs(
    state: AddItemState,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState
) {
    DatePickerWrapper(
        showDatePicker = state.showDatePicker,
        datePickerState = datePickerState,
        onDismiss = { state.showDatePicker = false },
        confirmButton = {
            datePickerState.selectedDateMillis?.let { millis ->
                val selectedDate = Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                state.updateSelectedDate(selectedDate)
            }
            state.showDatePicker = false
        },
        dismissButton = { state.showDatePicker = false }
    )

    TimePickerDialogWrapper(
        showTimePicker = state.showTimePicker,
        timePickerState = timePickerState,
        onDismiss = { state.showTimePicker = false },
        onConfirm = {
            state.updateTime(timePickerState.hour, timePickerState.minute)
            state.showTimePicker = false
        }
    )
}