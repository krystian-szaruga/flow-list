package com.olr261dn.task.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun DailyBreakdownCard(tasks: List<CompletionHistory>) {
    val theme = customTheme()

    val dailyBreakdown = remember(tasks) {
        tasks
            .groupBy {
                Instant.ofEpochMilli(it.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
            .mapValues { (_, items) ->
                Pair(items.count { it.isCompleted }, items.size)
            }
            .toList()
            .sortedByDescending { it.first }
            .take(5)
    }

    if (dailyBreakdown.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = theme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Recent Activity (Last 5 Days)",
                fontSize = 22.nonScaledSp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = theme.onSurface
            )

            dailyBreakdown.forEach { (date, counts) ->
                DailyBreakdownItem(date, counts.first, counts.second)
            }
        }
    }
}

@Composable
private fun DailyBreakdownItem(date: LocalDate, completed: Int, total: Int) {
    val theme = customTheme()
    val completionRate = if (total > 0) (completed.toFloat() / total) else 0f

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                fontSize = 14.nonScaledSp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = theme.onSurface
            )
            Text(
                text = "$completed / $total",
                fontSize = 14.nonScaledSp,
                style = MaterialTheme.typography.bodyMedium,
                color = theme.onSurface.copy(alpha = 0.7f)
            )
        }

        LinearProgressIndicator(
            progress = { completionRate },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = when {
                completionRate >= 0.7f -> theme.greenOnSecondary
                completionRate >= 0.4f -> Color(0xFFFF9800)
                else -> theme.redOnSecondary
            },
            trackColor = theme.container
        )
    }
}