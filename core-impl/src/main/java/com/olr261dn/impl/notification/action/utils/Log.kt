package com.olr261dn.impl.notification.action.utils

import android.util.Log

suspend fun <T> logAndRunSuspended(
    tag: String,
    prefix: String = "",
    block: suspend () -> T
): T {
    val result = block()
    Log.i(tag, "$prefix$result")
    return result
}

fun <T> logAndRun(
    tag: String,
    prefix: String = "",
    block: () -> T
): T {
    val result = block()
    Log.i(tag, "$prefix$result")
    return result
}