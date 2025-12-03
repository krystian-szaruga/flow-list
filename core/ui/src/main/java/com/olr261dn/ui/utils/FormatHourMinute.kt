package com.olr261dn.ui.utils

import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun formatHourMinute(hour: Int, minute: Int): String {
    val context = LocalContext.current
    return remember(hour, minute, context) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val is24Hour = DateFormat.is24HourFormat(context)
        val timeFormat = if (is24Hour) {
            SimpleDateFormat("HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("hh:mm a", Locale.getDefault())
        }
        timeFormat.format(calendar.time)
    }
}
