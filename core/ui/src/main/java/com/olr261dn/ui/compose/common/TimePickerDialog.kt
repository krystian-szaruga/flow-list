package com.olr261dn.ui.compose.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.olr261dn.resources.R
import com.olr261dn.ui.utils.nonScaledSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    timePickerState: TimePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val keyboardInput = rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    TimePickerDialogHeader(
                        title = stringResource(R.string.pick_time),
                        keyboardInput = keyboardInput.value,
                        onToggleInputMode = { keyboardInput.value = !keyboardInput.value }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    TimePickerDialogContent(
                        keyboardInput = keyboardInput.value,
                        timePickerState = timePickerState
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    ConfirmationButtonsRow(
                        confirmLabel = stringResource(R.string.ok),
                        onConfirm = onConfirm,
                        onDismiss = onDismiss
                    )
                }
            }
        }
    }
}

@Composable
private fun TimePickerDialogHeader(
    title: String,
    keyboardInput: Boolean,
    onToggleInputMode: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 40.nonScaledSp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(onClick = onToggleInputMode) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(
                    id = if (!keyboardInput)
                        R.drawable.baseline_keyboard_24
                    else
                        R.drawable.baseline_access_time_filled_24
                ),
                contentDescription = "Toggle Input Mode"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialogContent(
    keyboardInput: Boolean,
    timePickerState: TimePickerState
) {
    if (!keyboardInput) {
        TimePicker(
            state = timePickerState,
            layoutType = TimePickerDefaults.layoutType()
        )
    } else {
        TimeInput(state = timePickerState)
    }
}

