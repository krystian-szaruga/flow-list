package com.olr261dn.ui.utils

import android.content.Context
import android.text.format.DateUtils

fun formatDate(timestamp: Long, context: Context): String {
    return DateUtils.formatDateTime(
        context,
        timestamp,
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or
                DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_ABBREV_MONTH
    )
}
