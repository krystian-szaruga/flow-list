package com.olr261dn.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatLocalDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return date.format(formatter)
}