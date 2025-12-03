package com.olr261dn.task

import androidx.compose.runtime.Composable
import com.olr261dn.ui.compose.base.BaseFeatureNav
import com.olr261dn.ui.compose.base.Template
import java.util.UUID

object TaskNav: BaseFeatureNav() {

    @Composable
    override fun FeatureScreen(
        template: Template,
        onClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    ) {
        TasksMain.Content(
            title = "Tasks",
            screenTemplate = template,
            onDetailClick = { onClick(it) },
            onStatistic = { onStatistic() }
        )
    }

    @Composable
    override fun DetailScreen(taskId: UUID, onBackPress: () -> Unit) {
        TaskDetail.Content(taskId) { onBackPress() }
    }

    @Composable
    override fun StatisticScreen(onBackPress: () -> Unit) {
        TaskStats.Content { onBackPress.invoke() }
    }
}