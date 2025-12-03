package com.olr261dn.recurring

import androidx.compose.runtime.Composable
import com.olr261dn.ui.compose.base.BaseFeatureNav
import com.olr261dn.ui.compose.base.Template
import java.util.UUID

object RecurringTaskNav: BaseFeatureNav() {

    @Composable
    override fun FeatureScreen(
        template: Template,
        onClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    ) {
        RecurringMain.Content(
            title = "Daily Tasks",
            screenTemplate = template,
            onDetailClick = { onClick(it) },
            onStatistic = { onStatistic() }
        )
    }

    @Composable
    override fun DetailScreen(taskId: UUID, onBackPress: () -> Unit) {
        RecurringDetail.Content(taskId) { onBackPress() }
    }

    @Composable
    override fun StatisticScreen(onBackPress: () -> Unit) {
        RecurringStats.Content { onBackPress.invoke() }
    }
}