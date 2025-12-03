package com.olr261dn.task.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp


@Composable
internal fun PieChartCard(stats: Stats) {
    val theme = customTheme()

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
                text = "Task Distribution",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onSurface
            )

            if (stats.total > 0) {
                SimplePieChart(
                    completed = stats.completed,
                    incomplete = stats.incomplete
                )
            } else {
                Text(
                    text = "No data to display",
                    fontSize = 14.nonScaledSp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    textAlign = TextAlign.Center,
                    color = theme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun SimplePieChart(completed: Int, incomplete: Int) {
    val theme = customTheme()
    val total = completed + incomplete
    val completedAngle = 360f * completed / total

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(150.dp)
        ) {
            val radius = size.minDimension / 2
            val topLeft = Offset(
                (size.width - radius * 2) / 2,
                (size.height - radius * 2) / 2
            )

            drawArc(
                color = theme.greenOnSecondary,
                startAngle = -90f,
                sweepAngle = completedAngle,
                useCenter = true,
                size = Size(radius * 2, radius * 2),
                topLeft = topLeft
            )

            drawArc(
                color = theme.redOnSecondary,
                startAngle = -90f + completedAngle,
                sweepAngle = 360f - completedAngle,
                useCenter = true,
                size = Size(radius * 2, radius * 2),
                topLeft = topLeft
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LegendItem("Completed", theme.greenOnSecondary, completed)
            LegendItem("Incomplete", theme.redOnSecondary, incomplete)
        }
    }
}

@Composable
private fun LegendItem(label: String, color: Color, count: Int) {
    val theme = customTheme()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(
            text = "$label: $count",
            fontSize = 13.nonScaledSp,
            style = MaterialTheme.typography.bodyMedium,
            color = theme.onSurface
        )
    }
}