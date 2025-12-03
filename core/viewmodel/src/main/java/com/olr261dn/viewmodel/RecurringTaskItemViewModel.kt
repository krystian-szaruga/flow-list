package com.olr261dn.viewmodel

import com.olr261dn.domain.model.RecurringTask
import com.olr261dn.domain.scheduler.InitialScheduleCalculator
import com.olr261dn.domain.usecase.RecurringTaskActions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecurringTaskItemViewModel @Inject constructor(
    actions: RecurringTaskActions,
    private val _initialScheduleCalculator: InitialScheduleCalculator
): ItemViewModel<RecurringTask>(actions) {
    val initialScheduleCalculator: InitialScheduleCalculator
        get() = _initialScheduleCalculator
    private val sorter = RecurringTaskSorter(_initialScheduleCalculator)

    override fun sortedItems(items: List<RecurringTask>): List<RecurringTask> = sorter.sort(items)

    private class RecurringTaskSorter (
        private val initialScheduleCalculator: InitialScheduleCalculator
    ) {
        fun sort(items: List<RecurringTask>): List<RecurringTask> {
            return items
                .map { task ->
                    val effectiveTime = initialScheduleCalculator.getEffectiveTime(task)
                    task to effectiveTime
                }
                .sortedWith(
                    compareBy<Pair<RecurringTask, Long>> { (task, _) -> task.isDisabled }
                        .thenBy { (_, time) -> time }
                )
                .map { (task, _) -> task }
        }
    }
}
