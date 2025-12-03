package com.olr261dn.domain.scheduler

import com.olr261dn.domain.model.RepeatPattern

interface NextScheduleCalculator {
    fun calculateNext(
        lastScheduledTime: Long,
        pattern: RepeatPattern?
    ): Long?
}

