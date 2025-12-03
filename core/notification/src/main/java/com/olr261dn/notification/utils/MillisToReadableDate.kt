package com.olr261dn.notification.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.millisToReadableDate(pattern: String = "yyyy-MMM-dd HH:mm:ss"): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
