package com.olr261dn.project

import androidx.compose.runtime.Composable
import com.olr261dn.ui.compose.base.BaseFeatureNav
import com.olr261dn.ui.compose.base.Template
import java.util.UUID

object ProjectNav: BaseFeatureNav() {
    @Composable
    override fun FeatureScreen(
        template: Template,
        onClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    ) {
        ProjectMain.Content(
            title = "Goals",
            screenTemplate = template,
            onDetailClick = { onClick(it) },
            onStatistic = { onStatistic() }
        )
    }

    @Composable
    override fun DetailScreen(taskId: UUID, onBackPress: () -> Unit) {
        ProjectDetail.Content(taskId) { onBackPress() }
    }

    @Composable
    override fun StatisticScreen(onBackPress: () -> Unit) {
        ProjectStats.Show { onBackPress.invoke() }
    }
}