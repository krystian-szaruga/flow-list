package com.olr261dn.notification.utils

import android.content.Intent

internal object IntentExtractor {
    fun extract(intent: Intent): IntentData = IntentData(
        intent.getLongExtra("id", -1),
        intent.getStringExtra("reminderName") ?: "",
        intent.getStringExtra("itemId") ?: ""
    )
}