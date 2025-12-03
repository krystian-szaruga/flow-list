package com.olr261dn.project.stats

import com.olr261dn.domain.model.CompletionHistory
import java.util.UUID

internal object GoalStatsAggregator {
    fun aggregate(goalStatsMap: Map<UUID, GoalStatsData>): GoalStatsData {
        var totalGoalCompletions = 0
        var totalGoalEntries = 0
        var totalUniqueTasks = 0
        var totalUniqueTasksCompleted = 0
        var totalUniqueTasksNotCompleted = 0
        var totalPendingTasks = 0
        var numberOfLinkedTasks = 0

        var latestGoalUpdate: Pair<CompletionHistory, String>? = null
        var latestTaskUpdate: Pair<CompletionHistory, String>? = null

        goalStatsMap.values.forEach { stats ->
            totalGoalCompletions += stats.goalCompletionCount
            totalGoalEntries += stats.goalTotalEntries
            totalUniqueTasks += stats.totalHistoryEntriesCount
            totalUniqueTasksCompleted += stats.completedEntriesCount
            totalUniqueTasksNotCompleted += stats.notCompletedEntriesCount
            totalPendingTasks += stats.tasksWithoutHistoryCount
            numberOfLinkedTasks += stats.totalLinkedTasksCount


            stats.lastGoalUpdate?.let { update ->
                if (latestGoalUpdate == null ||
                    update.markedAt > latestGoalUpdate!!.first.markedAt) {
                    latestGoalUpdate = update to stats.goalTitle
                }
            }

            stats.lastTaskUpdate?.let { update ->
                if (latestTaskUpdate == null ||
                    update.markedAt > latestTaskUpdate!!.first.markedAt) {
                    latestTaskUpdate = update to stats.goalTitle
                }
            }
        }

        return GoalStatsData(
            goalId = UUID.randomUUID(),
            goalCompletionCount = totalGoalCompletions,
            goalTotalEntries = totalGoalEntries,
            totalHistoryEntriesCount = totalUniqueTasks,
            completedEntriesCount = totalUniqueTasksCompleted,
            notCompletedEntriesCount = totalUniqueTasksNotCompleted,
            tasksWithoutHistoryCount = totalPendingTasks,
            lastGoalUpdate = latestGoalUpdate?.first,
            lastTaskUpdate = latestTaskUpdate?.first,
            totalLinkedTasksCount = numberOfLinkedTasks,
            lastGoalUpdateGoalTitle = latestGoalUpdate?.second,
            lastTaskUpdateGoalTitle = latestTaskUpdate?.second
        )
    }
}