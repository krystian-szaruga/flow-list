package com.olr261dn.ui.compose.common.popup

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.RepeatPattern
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.ConfirmationButtonsRow
import com.olr261dn.ui.compose.common.OverlayDialog
import com.olr261dn.ui.compose.common.TaskFormFields
import com.olr261dn.ui.utils.nonScaledSp
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemPopup(
    confirmLabel: Int = R.string.save,
    titleLabel: Int = R.string.title_label,
    id: UUID? = null,
    title: String = "",
    description: String = "",
    scheduledDateTime: Long = 0L,
    repeatPattern: RepeatPattern = RepeatPattern.Once,
    onDismiss: () -> Unit,
    additionalContent: @Composable () -> Unit = {},
    showSchedulingOptions: Boolean = false,
    maxScheduledTime: Long? = null,
    minScheduledTime: Long? = null,
    onScheduledTimeChanged: ((Long) -> Unit)? = null,
    onAdd: (UUID?, String, String, Long, RepeatPattern) -> Unit
) {
    val state = rememberAddItemState(
        initialTitle = title,
        initialDescription = description,
        initialDateTime = scheduledDateTime,
        allowScheduleDisabling = showSchedulingOptions,
        initialRepeatPattern = repeatPattern,
        maxScheduledTime = maxScheduledTime,
        minScheduledTime = minScheduledTime
    )

    LaunchedEffect(maxScheduledTime, minScheduledTime) {
        state.updateScheduleLimits(maxScheduledTime, minScheduledTime)
    }

    LaunchedEffect(state.selectedDate, state.selectedHour, state.selectedMinute, state.isSchedulingDisabled) {
        onScheduledTimeChanged?.invoke(state.getScheduledMillis())
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(),
        selectableDates = PresentAndFutureDates()
    )

    val timePickerState = rememberTimePickerState(
        initialHour = state.selectedHour,
        initialMinute = state.selectedMinute
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    OverlayDialog(
        onDismiss = onDismiss,
        scrollState
    ) {
        AddItemHeader(id = id, titleLabel = titleLabel)
        TaskFormFields(
            title = state.title,
            onTitleChange = state::updateTitle,
            description = state.description,
            onDescriptionChange = state::updateDescription,
            titleLabel = stringResource(R.string.title_label),
            isTitleError = state.showTitleError
        )

        SchedulingSection(
            state = state,
            timePickerState = timePickerState,
            isEnabled = !state.isSchedulingDisabled
        )

        if (state.showScheduleConflictError) {
            ScheduleConflictError()
        }

        if (state.showScheduleBeforeTasksError) {
            ScheduleBeforeTasksError()
        }

        additionalContent()
        if (showSchedulingOptions) {
            if (!state.isSchedulingDisabled) {
                Spacer(modifier = Modifier.height(16.dp))
                RepeatPatternSelector(
                    selectedPattern = state.repeatPattern,
                    onPatternSelected = state::updateRepeatPattern
                )
            }
            DisableScheduleCheckbox(
                isDisabled = state.isSchedulingDisabled,
                onToggle = state::toggleScheduling
            )
        }
        ConfirmationButtonsRow(
            confirmLabel = stringResource(confirmLabel),
            onDismiss = onDismiss,
            onConfirm = {
                state.validate()
                if (state.isValid) {
                    onAdd(
                        id,
                        state.title,
                        state.description,
                        state.getScheduledMillis(),
                        state.getFinalRepeatPattern()
                    )
                    onDismiss()
                } else {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                }
            }
        )
        DateTimePickerDialogs(
            state = state,
            datePickerState = datePickerState,
            timePickerState = timePickerState
        )
    }
}

@Composable
private fun rememberAddItemState(
    initialTitle: String,
    initialDescription: String,
    initialDateTime: Long,
    allowScheduleDisabling: Boolean,
    initialRepeatPattern: RepeatPattern = RepeatPattern.Once,
    maxScheduledTime: Long? = null,
    minScheduledTime: Long? = null // NEW
): AddItemState {
    return rememberSaveable(
        saver = AddItemState.Saver
    ) {
        AddItemState(
            initialTitle = initialTitle,
            initialDescription = initialDescription,
            initialDateTime = initialDateTime,
            allowScheduleDisabling = allowScheduleDisabling,
            initialRepeatPattern = initialRepeatPattern,
            maxScheduledTime = maxScheduledTime,
            minScheduledTime = minScheduledTime // NEW
        )
    }
}

@Composable
private fun ScheduleConflictError() {
    Text(
        text = stringResource(R.string.task_after_project_error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
    )
}

@Composable
private fun ScheduleBeforeTasksError() {
    Text(
        text = stringResource(R.string.project_before_tasks_error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
    )
}


@Composable
private fun AddItemHeader(id: UUID?, titleLabel: Int) {
    Text(
        text = stringResource(if (id == null) titleLabel else R.string.edit),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )
}



@Composable
private fun DisableScheduleCheckbox(
    isDisabled: Boolean,
    onToggle: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isDisabled,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = stringResource(R.string.disable_deadline),
            fontSize = 20.nonScaledSp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private class PresentAndFutureDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val date = Instant.ofEpochMilli(utcTimeMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return !date.isBefore(LocalDate.now())
    }
}