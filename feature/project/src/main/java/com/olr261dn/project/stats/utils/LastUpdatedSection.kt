package com.olr261dn.project.stats.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.olr261dn.project.stats.GoalStatsData
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.utils.formatTime
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun LastUpdatesSection(stats: GoalStatsData, theme: ThemeColor) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = theme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // ✅ Goal update
        stats.lastGoalUpdate?.let { update ->
            UpdateItem(
                label = stringResource(R.string.last_goal_update_label),
                goalTitle = stats.lastGoalUpdateGoalTitle,
                timestamp = formatTime(update.markedAt, context),
                theme = theme
            )
        }

        // ✅ Task update
        stats.lastTaskUpdate?.let { update ->
            UpdateItem(
                label = stringResource(R.string.last_task_update_label),
                goalTitle = stats.lastTaskUpdateGoalTitle,
                timestamp = formatTime(update.markedAt, context),
                theme = theme
            )
        }
    }
}

@Composable
private fun UpdateItem(
    label: String,
    goalTitle: String?,
    timestamp: String,
    theme: ThemeColor
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append(label)
                goalTitle?.let {
                    append(" (")
                    append(it)
                    append(")")
                    append(":")
                }
            },
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.nonScaledSp,
            color = theme.onSurface.copy(alpha = 0.7f),
            maxLines = 1,  // ✅ Max 1 linia
            overflow = TextOverflow.Ellipsis,  // ✅ ... na końcu
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = timestamp,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.nonScaledSp,
            color = theme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 4.dp)  // Lekkie wcięcie dla czytelności
        )
    }
}