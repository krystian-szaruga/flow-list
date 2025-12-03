package com.olr261dn.ui.compose.common.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.ScheduledDateField
import com.olr261dn.ui.compose.common.TimePickerButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SchedulingSection(
    state: AddItemState,
    timePickerState: TimePickerState,
    isEnabled: Boolean
) {
    DisableableContent(disabled = !isEnabled) {
        ScheduledDateField(
            selectedDate = state.selectedDate,
            onClick = { if (isEnabled) state.showDatePicker = true }
        )
    }

    DisableableContent(disabled = !isEnabled) {
        TimePickerButton(
            timePickerState = timePickerState,
            contentDescription = stringResource(R.string.pick_date),
            isError = state.showDateTimeError,
            onClick = { if (isEnabled) state.showTimePicker = true }
        )
    }

    if (state.showDateTimeError) {
        Text(
            text = stringResource(R.string.error_past_datetime),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun DisableableContent(
    disabled: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.alpha(if (disabled) 0.5f else 1f)) {
        content()
    }
}