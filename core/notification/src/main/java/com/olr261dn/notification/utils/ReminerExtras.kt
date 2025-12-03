package com.olr261dn.notification.utils

import android.content.Intent
import java.util.UUID

fun Intent.putReminderExtras(
    id: Long = -1,
    reminderName: String = "",
    itemId: UUID = UUID.randomUUID()
): Intent {
    putExtra("id", id)
    putExtra("reminderName", reminderName)
    putExtra("itemId", itemId.toString())
    return this
}