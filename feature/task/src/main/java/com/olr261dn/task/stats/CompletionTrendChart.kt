package com.olr261dn.task.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun CompletionTrendCard(tasks: List<CompletionHistory>) {
    val theme = customTheme()

    val completionsByDate = remember(tasks) {
        tasks
            .groupBy {
                Instant.ofEpochMilli(it.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
            .mapValues { (_, items) ->
                items.count { it.isCompleted }
            }
            .toList()
            .sortedBy { it.first }
            .takeLast(7)
    }

    if (completionsByDate.isEmpty()) return

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
                text = "Completion Trend (Last 7 Days)",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onSurface
            )

            HorizontalProgressChart(completionsByDate)
        }
    }
}

@Composable
private fun HorizontalProgressChart(data: List<Pair<LocalDate, Int>>) {
    val theme = customTheme()

    if (data.isEmpty()) return

    val maxValue = data.maxOfOrNull { it.second }?.coerceAtLeast(1) ?: 1

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { (date, count) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("MM/dd")),
                    fontSize = 13.nonScaledSp,
                    style = MaterialTheme.typography.bodySmall,
                    color = theme.onSurface,
                    modifier = Modifier.width(40.dp)
                )

                LinearProgressIndicator(
                    progress = { count.toFloat() / maxValue },
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = theme.greenOnSecondary,
                    trackColor = theme.container
                )

                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 13.nonScaledSp,
                    color = theme.onSurface,
                    modifier = Modifier.width(30.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}