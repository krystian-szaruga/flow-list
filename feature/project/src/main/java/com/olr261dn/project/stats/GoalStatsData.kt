package com.olr261dn.project.stats

import androidx.compose.runtime.Immutable
import com.olr261dn.domain.model.CompletionHistory
import java.util.UUID

@Immutable
internal data class GoalStatsData(
    val goalId: UUID,
    val goalTitle: String = "",
    val goalCompletionCount: Int,
    val goalTotalEntries: Int,
    val totalHistoryEntriesCount: Int,
    val completedEntriesCount: Int,
    val notCompletedEntriesCount: Int,
    val tasksWithoutHistoryCount: Int,
    val lastGoalUpdate: CompletionHistory?,
    val lastTaskUpdate: CompletionHistory?,
    val totalLinkedTasksCount: Int,
    val lastGoalUpdateGoalTitle: String? = null,
    val lastTaskUpdateGoalTitle: String? = null
)