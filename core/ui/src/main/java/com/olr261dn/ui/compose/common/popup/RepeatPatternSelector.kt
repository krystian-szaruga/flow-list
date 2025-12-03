package com.olr261dn.ui.compose.common.popup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.resources.R
import com.olr261dn.ui.utils.nonScaledSp

@Composable
fun RepeatPatternSelector(
    selectedPattern: RepeatPattern,
    onPatternSelected: (RepeatPattern) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEveryXDaysDialog by remember { mutableStateOf(false) }
    var intervalDays by remember { mutableStateOf("1") }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(R.string.repe),
            style = MaterialTheme.typography.labelLarge,
            fontSize = 30.nonScaledSp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selectedPattern is RepeatPattern.Once,
                    onClick = { onPatternSelected(RepeatPattern.Once) }
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedPattern is RepeatPattern.Once,
                onClick = { onPatternSelected(RepeatPattern.Once) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.once))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selectedPattern is RepeatPattern.EveryXDays,
                    onClick = { showEveryXDaysDialog = true }
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedPattern is RepeatPattern.EveryXDays,
                onClick = { showEveryXDaysDialog = true }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = when (selectedPattern) {
                    is RepeatPattern.EveryXDays ->
                        stringResource(R.string.every_x_days, selectedPattern.interval)
                    else -> stringResource(R.string.every_x_days_label)
                }
            )
        }
    }

    if (showEveryXDaysDialog) {
        AlertDialog(
            onDismissRequest = { showEveryXDaysDialog = false },
            title = { Text(stringResource(R.string.repeat_every_x_days)) },
            text = {
                OutlinedTextField(
                    value = intervalDays,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            intervalDays = it
                        }
                    },
                    label = { Text(stringResource(R.string.number_of_days)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val interval = intervalDays.toLongOrNull() ?: 1L
                        if (interval > 0) {
                            onPatternSelected(RepeatPattern.EveryXDays(interval))
                            showEveryXDaysDialog = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEveryXDaysDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}