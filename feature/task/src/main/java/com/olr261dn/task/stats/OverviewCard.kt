package com.olr261dn.task.stats
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp

@Composable
internal fun OverviewCard(stats: Stats) {
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
                text = "Overview",
                fontSize = 22.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onSurface
            )

            if (stats.total > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        label = "Total",
                        value = stats.total.toString(),
                        color = theme.onSurface
                    )
                    StatItem(
                        label = "Completed",
                        value = stats.completed.toString(),
                        color = theme.greenOnSecondary
                    )
                    StatItem(
                        label = "Incomplete",
                        value = stats.incomplete.toString(),
                        color = theme.redOnSecondary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                CompletionRateSection(stats.completionRate, theme)
            } else {
                Text(
                    text = "No tasks yet",
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
private fun CompletionRateSection(completionRate: Float, theme: ThemeColor) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Completion Rate",
                fontSize = 14.nonScaledSp,
                color = theme.onSurface
            )
            Text(
                text = "${completionRate.toInt()}%",
                fontSize = 14.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { completionRate / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = theme.greenOnSecondary,
            trackColor = theme.container
        )
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color) {
    val theme = customTheme()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 28.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.nonScaledSp,
            color = theme.onSurface.copy(alpha = 0.7f)
        )
    }
}