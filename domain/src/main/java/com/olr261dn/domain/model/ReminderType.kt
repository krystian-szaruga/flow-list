package com.olr261dn.domain.model

enum class ReminderType {
    DAILY,
    GOAL,
    TASK;

    companion object {
        fun fromName(name: String?): ReminderType? =
            entries.find { it.name.equals(name, ignoreCase = true) }
    }
}