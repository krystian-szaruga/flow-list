package com.olr261dn.ui.compose.common

import androidx.compose.material3.DatePickerState
import androidx.compose.runtime.MutableState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun onConfirmButton(
    datePickerState: DatePickerState,
    selectedDate: MutableState<LocalDate>,
    isPastDateTime: MutableState<Boolean>,
    showDatePicker: MutableState<Boolean>
) {
    datePickerState.selectedDateMillis?.let { millis ->
        selectedDate.value = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        isPastDateTime.value = false
    }
    showDatePicker.value = false
}