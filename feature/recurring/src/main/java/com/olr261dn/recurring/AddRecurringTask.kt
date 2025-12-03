package com.olr261dn.recurring

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.olr261dn.domain.enums.RecurringScheduleType
import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.scheduler.InitialScheduleCalculator
import com.olr261dn.recurring.utils.CustomDaysDisplay
import com.olr261dn.recurring.utils.DayPickerDialog
import com.olr261dn.recurring.utils.ScheduleTypeSelector
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.common.ConfirmationButtonsRow
import com.olr261dn.ui.compose.common.OverlayDialog
import com.olr261dn.ui.compose.common.StateToggle
import com.olr261dn.ui.compose.common.TaskFormFields
import com.olr261dn.ui.compose.common.TimePickerButton
import com.olr261dn.ui.compose.common.TimePickerDialogWrapper
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

internal class AddRecurringTask(
    @field:StringRes private val confirmLabel: Int = R.string.save,
    private val initialScheduleCalculator: InitialScheduleCalculator,
    private val id: UUID? = null,
    private val title: String = "",
    private val description: String = "",
    private val isDisabled: Boolean = false,
    private val scheduleType: RecurringScheduleType = RecurringScheduleType.EVERY_DAY,
    private val hour: Int? = null,
    private val minute: Int? = null,
    private val customDays: Set<DayOfWeek> = emptySet()
) {
    @Composable
    fun Show(onDismiss: () -> Unit, onAdd: (RecurringTask) -> Unit) {
        OverlayDialog(onDismiss = onDismiss) {
            PopupContent(onAdd = onAdd, onDismiss = onDismiss)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PopupContent(onAdd: (RecurringTask) -> Unit, onDismiss: () -> Unit) {
        val title = rememberSaveable { mutableStateOf(title) }
        val description = rememberSaveable { mutableStateOf(description) }
        val isDisabled = rememberSaveable { mutableStateOf(isDisabled) }
        val scheduleType = rememberSaveable { mutableStateOf(scheduleType) }
        val customDays = rememberSaveable { mutableStateOf(customDays) }
        val showTimePicker = rememberSaveable { mutableStateOf(false) }
        val showDayPicker = rememberSaveable { mutableStateOf(false) }
        val timePickerState = rememberTimePickerStateWithDefault(hour, minute)
        val addingTaskNotSuccessful = rememberSaveable { mutableStateOf(false) }
        val isError = title.value.isEmpty() && addingTaskNotSuccessful.value

        Text(
            text = stringResource(R.string.recurring_task),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        TaskFormFields(
            title = title.value,
            onTitleChange = { title.value = it },
            description = description.value,
            onDescriptionChange = { description.value = it },
            titleLabel = stringResource(R.string.title_label),
            isTitleError = isError
        )
        TimePickerButton(
            timePickerState = timePickerState,
            contentDescription = stringResource(R.string.pick_time),
            onClick = { showTimePicker.value = true }
        )
        StateToggle(isDisabled.value, R.string.disable_daily_task) { isDisabled.value = it }
        ScheduleTypeSelector(
            selectedType = scheduleType.value,
            onTypeSelected = {
                scheduleType.value = it
                if (it == RecurringScheduleType.CUSTOM_DAYS) {
                    showDayPicker.value = true
                } else {
                    customDays.value = emptySet()
                }
            }
        )
        if (scheduleType.value == RecurringScheduleType.CUSTOM_DAYS) {
            CustomDaysDisplay(
                selectedDays = customDays.value,
                onClick = { showDayPicker.value = true }
            )
        }
        ConfirmationButtonsRow(
            confirmLabel = stringResource(this.confirmLabel),
            onDismiss = onDismiss,
            onConfirm = {
                if (title.value.isNotBlank()) {
                    val isValidSchedule = scheduleType.value != RecurringScheduleType.CUSTOM_DAYS ||
                            customDays.value.isNotEmpty()
                    if (isValidSchedule) {
                        onAdd(
                            RecurringTask(
                                id = id ?: UUID.randomUUID(),
                                title = title.value,
                                description = description.value,
                                scheduledTime = initialScheduleCalculator.calculateInitial(
                                    timePickerState.hour,
                                    timePickerState.minute,
                                    scheduleType.value,
                                    customDays.value
                                ),
                                delayedTime = null,
                                isDisabled = isDisabled.value,
                                scheduleType = scheduleType.value,
                                customDays = customDays.value
                            )
                        )
                        onDismiss()
                    } else {
                        addingTaskNotSuccessful.value = true
                    }
                } else {
                    addingTaskNotSuccessful.value = true
                }
            }
        )

        TimePickerDialogWrapper(
            showTimePicker = showTimePicker.value,
            timePickerState = timePickerState,
            onDismiss = { showTimePicker.value = false },
            onConfirm = { showTimePicker.value = false }
        )

        if (showDayPicker.value) {
            DayPickerDialog(
                selectedDays = customDays.value,
                onDismiss = { showDayPicker.value = false },
                onConfirm = { days ->
                    val normalizedType = normalizeScheduleType(days)
                    scheduleType.value = normalizedType
                    customDays.value = getSelectedDays(normalizedType, days)
                    showDayPicker.value = false
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun rememberTimePickerStateWithDefault(
        hour: Int?,
        minute: Int?
    ): TimePickerState {
        return hour?.let { h ->
            minute?.let { m ->
                rememberTimePickerState(h, m)
            }
        } ?: run {
            val now = LocalTime.now()
            rememberTimePickerState(now.hour, now.minute)
        }
    }

    private fun normalizeScheduleType(selectedDays: Set<DayOfWeek>): RecurringScheduleType {
        return when {
            selectedDays.size == 7 -> RecurringScheduleType.EVERY_DAY
            selectedDays == setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY) -> RecurringScheduleType.WORKDAYS_ONLY
            selectedDays == setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) -> RecurringScheduleType.WEEKENDS_ONLY
            else -> RecurringScheduleType.CUSTOM_DAYS
        }
    }

    private fun getSelectedDays(
        normalizedType: RecurringScheduleType,
        days: Set<DayOfWeek>
    ): Set<DayOfWeek> {
        return if (normalizedType == RecurringScheduleType.CUSTOM_DAYS) days else emptySet()
    }
}

