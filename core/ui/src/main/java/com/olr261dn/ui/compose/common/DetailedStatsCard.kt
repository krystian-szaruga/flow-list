package com.olr261dn.ui.compose.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.CompletionHistory
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.ui.utils.toLocalDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DetailedStatsCard(
    percentage: Float,
    completed: Int,
    total: Int,
    stats: List<CompletionHistory>,
    onScrollToBottom: () -> Unit
) {
    val performanceColor = getPerformanceColor(percentage)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = customTheme().surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.statistics_overview),
                fontSize = 14.nonScaledSp,
                color = customTheme().onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    progress = { percentage },
                    color = performanceColor,
                    trackColor = customTheme().onSurface.copy(alpha = 0.1f),
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = "${(percentage * 100).toInt()}%",
                    fontSize = 36.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    color = performanceColor
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = stringResource(R.string.completed),
                    value = completed.toString(),
                    icon = Icons.Default.CheckCircle,
                    color = customTheme().getCompletedColor(true)
                )

                StatItem(
                    modifier = Modifier.clickable { onScrollToBottom() },
                    label = stringResource(R.string.total_task),
                    value = total.toString(),
                    icon = Icons.AutoMirrored.Filled.List,
                    color = customTheme().onSurface
                )

                StatItem(
                    label = stringResource(R.string.not_completed),
                    value = (total - completed).toString(),
                    icon = Icons.Default.Clear,
                    color = customTheme().getCompletedColor(false)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = customTheme().onSurface.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.weekly_trend),
                fontSize = 16.nonScaledSp,
                fontWeight = FontWeight.SemiBold,
                color = customTheme().onSurface,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(5.dp))
            WeeklyTrendCharts(stats = stats)
        }
    }
}

@Composable
private fun StatItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.nonScaledSp,
            color = customTheme().onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun WeeklyTrendCharts(stats: List<CompletionHistory>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(
                color = customTheme().getCompletedColor(true),
                label = stringResource(R.string.completed)
            )
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(
                color = customTheme().getCompletedColor(false),
                label = stringResource(R.string.not_completed)
            )
        }
        CombinedWeeklyTrendChart(stats = stats)
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(2.dp)
                )
        )
        Text(
            text = label,
            fontSize = 12.nonScaledSp,
            color = customTheme().onSurface.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CombinedWeeklyTrendChart(stats: List<CompletionHistory>) {
    val daysOfWeek = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )
    val today = LocalDate.now().dayOfWeek

    val dailyData = daysOfWeek.map { dayOfWeek ->
        val completed = stats.count { stat ->
            stat.markedAt.toLocalDate().dayOfWeek == dayOfWeek && stat.isCompleted
        }
        val notCompleted = stats.count { stat ->
            stat.markedAt.toLocalDate().dayOfWeek == dayOfWeek && !stat.isCompleted
        }
        Triple(dayOfWeek, completed, notCompleted)
    }
    val maxCount = dailyData.maxOfOrNull { maxOf(it.second, it.third) } ?: 1

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        items(dailyData) { (dayOfWeek, completedCount, notCompletedCount) ->
            DualChartBar(
                dayLabel = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).take(3),
                completedCount = completedCount,
                notCompletedCount = notCompletedCount,
                maxCount = maxCount,
                isToday = dayOfWeek == today
            )
        }
    }
}

@Composable
private fun DualChartBar(
    dayLabel: String,
    completedCount: Int,
    notCompletedCount: Int,
    maxCount: Int,
    isToday: Boolean
) {
    val completedHeightFraction = if (maxCount > 0) {
        (completedCount.toFloat() / maxCount).coerceAtLeast(0.05f)
    } else 0.05f

    val notCompletedHeightFraction = if (maxCount > 0) {
        (notCompletedCount.toFloat() / maxCount).coerceAtLeast(0.05f)
    } else 0.05f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(50.dp)
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(20.dp)
            ) {
                Text(
                    text = if (completedCount > 0) completedCount.toString() else "",
                    fontSize = 9.nonScaledSp,
                    color = customTheme().onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .fillMaxHeight(completedHeightFraction)
                        .background(
                            color = customTheme().getCompletedColor(true),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomStart = 2.dp,
                                bottomEnd = 2.dp
                            )
                        )
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(20.dp)
            ) {
                Text(
                    text = if (notCompletedCount > 0) notCompletedCount.toString() else "",
                    fontSize = 9.nonScaledSp,
                    color = customTheme().onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .fillMaxHeight(notCompletedHeightFraction)
                        .background(
                            color = customTheme().getCompletedColor(false),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomStart = 2.dp,
                                bottomEnd = 2.dp
                            )
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dayLabel,
            fontSize = 10.nonScaledSp,
            color = customTheme().onSurface,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

private fun getPerformanceColor(percentage: Float): Color {
    return when {
        percentage >= 0.8f -> Color(0xFF4CAF50)
        percentage >= 0.6f -> Color(0xFF8BC34A)
        percentage >= 0.4f -> Color(0xFFFF9800)
        percentage >= 0.2f -> Color(0xFFFF5722)
        else -> Color(0xFFF44336)
    }
}