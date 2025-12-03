package com.olr261dn.domain.model

import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
sealed class RepeatPattern {
    @Serializable
    object Once: RepeatPattern()

    @Serializable
    object Daily: RepeatPattern()

    @Serializable
    data class EveryXDays(val interval: Long) : RepeatPattern()

    @Serializable
    data class CustomDays(val daysOfWeek: Set<DayOfWeek>): RepeatPattern()
}