package com.olr261dn.task.stats

internal data class Stats(
    val total: Int,
    val completed: Int,
    val incomplete: Int,
    val completionRate: Float
)