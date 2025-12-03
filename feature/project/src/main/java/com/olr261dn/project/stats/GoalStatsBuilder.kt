package com.olr261dn.project.stats

import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.domain.model.GoalWithTasks
import com.olr261dn.domain.model.Project
import java.util.UUID

internal class GoalStatsBuilder(
    goals: List<CompletionHistory>,
    taskCompletionHistory: List<CompletionHistory>,
    goalsWithDetails: List<Project>,
    goalWithTasks: List<GoalWithTasks>
) {
    private val groupedGoals = goals.groupBy { it.projectId }
    private val groupedTaskHistory = taskCompletionHistory.groupBy { it.projectId }
    private val goalTitlesMap = goalsWithDetails.associateBy({ it.id }, { it.title })
    private val goalTasksMap = goalWithTasks.associateBy({ it.project.id }, { it.tasks })

    fun build(): Map<UUID, GoalStatsData> {
        return groupedGoals
            .mapNotNull { (goalId, goalHistory) ->
                goalId?.let { buildStatsForGoal(it, goalHistory) }
            }
            .sortedBy { it.second.goalTitle }
            .toMap(LinkedHashMap())
    }

    private fun buildStatsForGoal(
        goalId: UUID,
        goalHistory: List<CompletionHistory>
    ): Pair<UUID, GoalStatsData> {
        val pureGoalHistory = goalHistory.filter { it.taskId == null }
        val goalCompletions = pureGoalHistory.count { it.isCompleted }

        val tasksForGoal = groupedTaskHistory[goalId] ?: emptyList()
        val taskStats = calculateTaskStats(goalId, tasksForGoal)

        return goalId to GoalStatsData(
            goalId = goalId,
            goalTitle = goalTitlesMap[goalId] ?: "",
            goalCompletionCount = goalCompletions,
            goalTotalEntries = pureGoalHistory.size,
            completedEntriesCount = taskStats.completedHistoryEntries,
            totalHistoryEntriesCount = taskStats.totalHistoryEntries,
            notCompletedEntriesCount = taskStats.notCompletedHistoryEntries,
            tasksWithoutHistoryCount = taskStats.uniqueTasksWithoutHistory,
            totalLinkedTasksCount = taskStats.totalUniqueLinkedTasks,
            lastGoalUpdate = pureGoalHistory.maxByOrNull { it.markedAt },
            lastTaskUpdate = tasksForGoal.maxByOrNull { it.markedAt },
        )
    }

    private fun calculateTaskStats(
        goalId: UUID,
        taskHistoryEntries: List<CompletionHistory> 
    ): TaskStats {
        val allTasksForGoal = goalTasksMap[goalId] ?: emptyList()
        val totalHistoryEntries = taskHistoryEntries.size
        val completedHistoryEntries = taskHistoryEntries.count { it.isCompleted }
        val notCompletedHistoryEntries = taskHistoryEntries.count { !it.isCompleted }
        val totalUniqueLinkedTasks = allTasksForGoal.size
        val uniqueTaskIdsInHistory = taskHistoryEntries.mapNotNull { it.taskId }.toSet()
        val uniqueTasksWithoutHistory = totalUniqueLinkedTasks - uniqueTaskIdsInHistory.size
        return TaskStats(
            totalHistoryEntries = totalHistoryEntries,
            completedHistoryEntries = completedHistoryEntries,
            notCompletedHistoryEntries = notCompletedHistoryEntries,
            uniqueTasksWithoutHistory = uniqueTasksWithoutHistory,
            totalUniqueLinkedTasks = totalUniqueLinkedTasks
        )
    }


    private data class TaskStats(
        val totalHistoryEntries: Int,           // Total number of ALL history records
        val completedHistoryEntries: Int,       // ALL history entries where isCompleted = true
        val notCompletedHistoryEntries: Int,    // ALL history entries where isCompleted = false
        val uniqueTasksWithoutHistory: Int,     // UNIQUE tasks that have zero history entries
        val totalUniqueLinkedTasks: Int         // Total UNIQUE tasks linked to goal
    )
}