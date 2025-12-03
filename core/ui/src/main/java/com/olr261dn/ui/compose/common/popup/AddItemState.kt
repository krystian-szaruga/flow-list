package com.olr261dn.ui.compose.common.popup

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import com.olr261dn.domain.model.RepeatPattern
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Stable
internal class AddItemState(
    initialTitle: String,
    initialDescription: String,
    initialDateTime: Long,
    private val allowScheduleDisabling: Boolean,
    initialRepeatPattern: RepeatPattern = RepeatPattern.Once,
    private var maxScheduledTime: Long? = null,
    private var minScheduledTime: Long? = null
) {
    var title by mutableStateOf(initialTitle)
        private set

    var description by mutableStateOf(initialDescription)
        private set

    var isSchedulingDisabled by mutableStateOf(allowScheduleDisabling)
        private set

    var showDatePicker by mutableStateOf(false)
    var showTimePicker by mutableStateOf(false)

    private var validationAttempted by mutableStateOf(false)
    var isPastDateTime by mutableStateOf(false)
        private set

    var isAfterMaxScheduledTime by mutableStateOf(false)
        private set

    var isBeforeMinScheduledTime by mutableStateOf(false) // NEW
        private set

    private val initialDateTime = if (initialDateTime == 0L) {
        LocalDateTime.now().plusMinutes(1)
    } else {
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(initialDateTime),
            ZoneId.systemDefault()
        )
    }
    var repeatPattern by mutableStateOf(initialRepeatPattern)
        private set

    var selectedDate: LocalDate by mutableStateOf(this.initialDateTime.toLocalDate())
        private set

    var selectedHour by mutableIntStateOf(this.initialDateTime.hour)
        private set

    var selectedMinute by mutableIntStateOf(this.initialDateTime.minute)
        private set

    val showTitleError: Boolean
        get() = validationAttempted && title.isBlank()

    val showDateTimeError: Boolean
        get() = validationAttempted && isPastDateTime && !isSchedulingDisabled

    val showScheduleConflictError: Boolean
        get() = validationAttempted && isAfterMaxScheduledTime && !isSchedulingDisabled

    val showScheduleBeforeTasksError: Boolean // NEW
        get() = validationAttempted && isBeforeMinScheduledTime && !isSchedulingDisabled

    val isValid: Boolean
        get() = title.isNotBlank() &&
                (isSchedulingDisabled || (!isPastDateTime && !isAfterMaxScheduledTime && !isBeforeMinScheduledTime))

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateScheduleLimits(maxTime: Long?, minTime: Long?) {
        maxScheduledTime = maxTime
        minScheduledTime = minTime
        if (validationAttempted && !isSchedulingDisabled) {
            val scheduledMillis = getScheduledMillis()

            isAfterMaxScheduledTime = maxTime?.let { max ->
                scheduledMillis > 0L && scheduledMillis > max
            } ?: false

            isBeforeMinScheduledTime = minTime?.let { min ->
                scheduledMillis > 0L && scheduledMillis < min
            } ?: false
        }
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun toggleScheduling() {
        isSchedulingDisabled = !isSchedulingDisabled
        if (isSchedulingDisabled) {
            repeatPattern = RepeatPattern.Once
        }
    }

    fun updateRepeatPattern(pattern: RepeatPattern) {
        repeatPattern = pattern
    }

    fun updateSelectedDate(date: LocalDate) {
        selectedDate = date
        isPastDateTime = false
        isAfterMaxScheduledTime = false
        isBeforeMinScheduledTime = false // NEW
    }

    fun updateTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        isPastDateTime = false
        isAfterMaxScheduledTime = false
        isBeforeMinScheduledTime = false // NEW
    }

    fun validate() {
        validationAttempted = true
        if (!isSchedulingDisabled) {
            val scheduledMillis = getScheduledMillis()
            isPastDateTime = scheduledMillis <= System.currentTimeMillis()

            isAfterMaxScheduledTime = maxScheduledTime?.let { max ->
                scheduledMillis > 0L && scheduledMillis > max
            } ?: false

            isBeforeMinScheduledTime = minScheduledTime?.let { min ->
                scheduledMillis > 0L && scheduledMillis < min
            } ?: false
        }
    }

    fun getScheduledMillis(): Long {
        if (isSchedulingDisabled) return 0L

        return selectedDate
            .atTime(selectedHour, selectedMinute)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getFinalRepeatPattern(): RepeatPattern {
        return if (isSchedulingDisabled) RepeatPattern.Once else repeatPattern
    }

    companion object {
        val Saver: Saver<AddItemState, *> = listSaver(
            save = {
                listOf(
                    it.title,
                    it.description,
                    it.isSchedulingDisabled,
                    it.showDatePicker,
                    it.showTimePicker,
                    it.validationAttempted,
                    it.isPastDateTime,
                    it.selectedDate.toEpochDay(),
                    it.selectedHour,
                    it.selectedMinute,
                    it.allowScheduleDisabling,
                    serializeRepeatPattern(it.repeatPattern),
                    it.maxScheduledTime,
                    it.isAfterMaxScheduledTime,
                    it.minScheduledTime,
                    it.isBeforeMinScheduledTime
                )
            },
            restore = {
                AddItemState(
                    initialTitle = it[0] as String,
                    initialDescription = it[1] as String,
                    initialDateTime = 0L,
                    allowScheduleDisabling = it[10] as Boolean,
                    initialRepeatPattern = deserializeRepeatPattern(it[11] as String),
                    maxScheduledTime = it[12] as Long?,
                    minScheduledTime = it[14] as Long?
                ).apply {
                    isSchedulingDisabled = it[2] as Boolean
                    showDatePicker = it[3] as Boolean
                    showTimePicker = it[4] as Boolean
                    validationAttempted = it[5] as Boolean
                    isPastDateTime = it[6] as Boolean
                    selectedDate = LocalDate.ofEpochDay(it[7] as Long)
                    selectedHour = it[8] as Int
                    selectedMinute = it[9] as Int
                    isAfterMaxScheduledTime = it[13] as Boolean
                    isBeforeMinScheduledTime = it[15] as Boolean
                }
            }
        )

        private fun serializeRepeatPattern(pattern: RepeatPattern): String {
            return when (pattern) {
                is RepeatPattern.Once -> "once"
                is RepeatPattern.Daily -> "daily"
                is RepeatPattern.EveryXDays -> "every_x_days:${pattern.interval}"
                is RepeatPattern.CustomDays -> {
                    val days = pattern.daysOfWeek.joinToString(",") { it.value.toString() }
                    "weekly:$days"
                }
            }
        }

        private fun deserializeRepeatPattern(serialized: String): RepeatPattern {
            return when {
                serialized == "once" -> RepeatPattern.Once
                serialized == "daily" -> RepeatPattern.Daily
                serialized.startsWith("every_x_days:") -> {
                    val interval = serialized.substringAfter(":").toLongOrNull() ?: 1L
                    RepeatPattern.EveryXDays(interval)
                }

                serialized.startsWith("weekly:") -> {
                    val days = serialized.substringAfter(":")
                        .split(",")
                        .mapNotNull { it.toIntOrNull() }
                        .mapNotNull { DayOfWeek.of(it) }
                        .toSet()
                    RepeatPattern.CustomDays(days)
                }

                else -> RepeatPattern.Once
            }
        }
    }
}