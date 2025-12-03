package com.olr261dn.ui.utils

import android.content.Context
import android.text.format.DateUtils


fun formatTime(
    timestamp: Long,
    context: Context,
    format: Int = DateUtils.FORMAT_SHOW_TIME
): String {
    if (timestamp == 0L) return "Not scheduled"
    return DateUtils.formatDateTime(
        context,
        timestamp,
        format
    )
}