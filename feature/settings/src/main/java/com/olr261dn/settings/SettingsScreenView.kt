package com.olr261dn.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.resources.R
import com.olr261dn.ui.compose.base.Template
import com.olr261dn.ui.theme.ThemeColor
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.ui.utils.nonScaledSp
import com.olr261dn.viewmodel.PreferencesViewModel

object SettingsScreenView {
    @Composable
    fun Screen(screenTemplate: Template) {
        val theme = customTheme()
        val viewModel = hiltViewModel<PreferencesViewModel>()
        val navbarPosition = viewModel.navBarPosition.collectAsState().value
        val quickActionPosition = viewModel.quickActionPosition.collectAsState().value
        val recurringTaskDelay = viewModel.recurringTaskDelay.collectAsState().value
        val taskDelay = viewModel.taskDelay.collectAsState().value
        val biometricStatus = viewModel.biometricStatus.collectAsState().value

        screenTemplate.Screen(
            title = stringResource(R.string.settings_title),
            showFabContent = false
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SettingsSection(
                        title = stringResource(R.string.settings_navigation_bar_position),
                        theme = theme
                    ) {
                        NavigationBarPositionSelector(
                            selectedPosition = navbarPosition,
                            theme = theme
                        ) {
                            viewModel.setNavbarPosition(it)
                        }
                    }
                }

                item {
                    SettingsSection(
                        title = stringResource(R.string.settings_fab_position),
                        theme = theme
                    ) {
                        FABPositionSelector(
                            selectedPosition = quickActionPosition,
                            theme = theme
                        ) {
                            viewModel.setFabPosition(it)
                        }
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = theme.onBackground.copy(alpha = 0.2f)
                    )
                }

                item {
                    SettingsSection(
                        title = stringResource(R.string.settings_recurring_task_delay),
                        theme = theme
                    ) {
                        RecurringTaskDelaySelector(
                            selectedDelay = recurringTaskDelay,
                            theme = theme
                        ) {
                            viewModel.setRecurringTaskDelay(it)
                        }
                    }
                }

                item {
                    SettingsSection(
                        title = stringResource(R.string.settings_task_delay),
                        theme = theme
                    ) {
                        TaskDelaySelector(
                            selectedDelay = taskDelay,
                            theme = theme
                        ) {
                            viewModel.setTaskDelay(it)
                        }
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = theme.onBackground.copy(alpha = 0.2f)
                    )
                }
                item {
                    SettingsSection(
                        title = stringResource(R.string.settings_security),
                        theme = theme
                    ) {
                        BiometricLockToggle(
                            isEnabled = biometricStatus,
                            theme = theme
                        ) {
                            viewModel.setBiometricStatus(it)
                        }
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = theme.onBackground.copy(alpha = 0.2f)
                    )
                }

                item {
                    AboutAppSection(theme = theme)
                }
            }
        }
    }

    @Composable
    private fun SettingsSection(
        title: String,
        theme: ThemeColor,
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.nonScaledSp,
                fontWeight = FontWeight.Bold,
                color = theme.onBackground
            )
            content()
        }
    }

    @Composable
    private fun NavigationBarPositionSelector(
        selectedPosition: NavbarPosition?,
        theme: ThemeColor,
        onChange: (NavbarPosition) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavbarPosition.entries.forEach { position ->
                PositionOption(
                    text = position.toLabel(),
                    isSelected = selectedPosition == position,
                    onClick = {
                        onChange(position)
                    },
                    theme = theme
                )
            }
        }
    }

    @Composable
    private fun FABPositionSelector(
        selectedPosition: QuickActionPosition?,
        theme: ThemeColor,
        onChange: (QuickActionPosition) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionPosition.entries.forEach { position ->
                PositionOption(
                    text = position.toLabel(),
                    isSelected = selectedPosition == position,
                    onClick = {
                        onChange(position)
                    },
                    theme = theme
                )
            }
        }
    }

    @Composable
    private fun RecurringTaskDelaySelector(
        selectedDelay: Long?,
        theme: ThemeColor,
        onChange: (Long) -> Unit
    ) {
        if (selectedDelay == null) return
        val delayOptions = listOf(
            10L * 60 * 1000 to stringResource(R.string.settings_delay_10_min),
            15L * 60 * 1000 to stringResource(R.string.settings_delay_15_min),
            20L * 60 * 1000 to stringResource(R.string.settings_delay_20_min),
            1L * 60 * 60 * 1000 to stringResource(R.string.settings_delay_1_hour),
            2L * 60 * 60 * 1000 to stringResource(R.string.settings_delay_2_hours)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            delayOptions.forEach { (delay, label) ->
                DelayOption(
                    text = label,
                    isSelected = selectedDelay == delay,
                    onClick = {
                        onChange(delay)
                    },
                    theme = theme
                )
            }
        }
    }

    @Composable
    private fun TaskDelaySelector(
        selectedDelay: Long?,
        theme: ThemeColor,
        onChange: (Long) -> Unit
    ) {
        if (selectedDelay == null) return

        val delayOptions = listOf(
            3L * 60 * 60 * 1000 to stringResource(R.string.settings_delay_3_hours),
            5L * 60 * 60 * 1000 to stringResource(R.string.settings_delay_5_hours),
            1L * 24 * 60 * 60 * 1000 to stringResource(R.string.settings_delay_1_day),
            2L * 24 * 60 * 60 * 1000 to stringResource(R.string.settings_delay_2_days),
            7L * 24 * 60 * 60 * 1000 to stringResource(R.string.settings_delay_1_week)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            delayOptions.forEach { (delay, label) ->
                DelayOption(
                    text = label,
                    isSelected = selectedDelay == delay,
                    onClick = {
                        onChange(delay)
                    },
                    theme = theme
                )
            }
        }
    }

    @Composable
    private fun PositionOption(
        text: String,
        isSelected: Boolean,
        onClick: () -> Unit,
        theme: ThemeColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isSelected) theme.selectedSurface
                    else Color.Transparent
                )
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 16.nonScaledSp,
                    color = if (isSelected)
                        theme.onSelectedSurface
                    else
                        theme.onSurface
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = theme.primary,
                    unselectedColor = theme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }

    @Composable
    private fun DelayOption(
        text: String,
        isSelected: Boolean,
        onClick: () -> Unit,
        theme: ThemeColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isSelected) theme.selectedSurface
                    else Color.Transparent
                )
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 16.nonScaledSp,
                    color = if (isSelected)
                        theme.onSelectedSurface
                    else
                        theme.onSurface
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = theme.primary,
                    unselectedColor = theme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }

    @Composable
    private fun AboutAppSection(theme: ThemeColor) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(theme.container)
                .clickable { isExpanded = !isExpanded }
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = theme.onContainer,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = stringResource(R.string.settings_about_app),
                        fontSize = 22.nonScaledSp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onContainer
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(
                        if (isExpanded) R.string.settings_collapse
                        else R.string.settings_expand
                    ),
                    tint = theme.onContainer,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(if (isExpanded) 0f else 0f)
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = theme.onContainer.copy(alpha = 0.2f)
            )

            Text(
                text = stringResource(R.string.settings_about_description),
                fontSize = 14.nonScaledSp,
                color = theme.onContainer,
                lineHeight = 22.nonScaledSp
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = tween(300)
                ) + fadeIn(
                    animationSpec = tween(300)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(300)
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.settings_about_technologies),
                            fontSize = 16.nonScaledSp,
                            fontWeight = FontWeight.SemiBold,
                            color = theme.onContainer
                        )

                        TechStack(theme = theme)
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.settings_about_features),
                            fontSize = 16.nonScaledSp,
                            fontWeight = FontWeight.SemiBold,
                            color = theme.onContainer
                        )

                        FeaturesList(theme = theme)
                    }

                    // Version
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.settings_about_version),
                            fontSize = 12.nonScaledSp,
                            color = theme.onContainer.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "1.0.0",
                            fontSize = 12.nonScaledSp,
                            fontWeight = FontWeight.Medium,
                            color = theme.onContainer.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun TechStack(theme: ThemeColor) {
        val items = listOf(
            "Jetpack Compose",
            "Hilt & Dagger",
            "Kotlin Coroutines",
            "Room Database",
            "WorkManager",
            "Material Design 3",
            "Clean Architecture",
            "Multi-Module"
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        Surface(
                            modifier = Modifier.weight(1f),
                            color = theme.surface.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                fontSize = 12.nonScaledSp,
                                color = theme.onContainer
                            )
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

    @Composable
    private fun FeaturesList(theme: ThemeColor) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FeatureItem(
                icon = Icons.Outlined.CheckCircle,
                text = stringResource(R.string.settings_feature_daily_tasks),
                theme = theme
            )
            FeatureItem(
                icon = Icons.Outlined.Notifications,
                text = stringResource(R.string.settings_feature_notifications),
                theme = theme
            )
            FeatureItem(
                icon = Icons.Outlined.DateRange,
                text = stringResource(R.string.settings_feature_goal_tracking),
                theme = theme
            )
            FeatureItem(
                icon = Icons.Outlined.Settings,
                text = stringResource(R.string.settings_feature_customization),
                theme = theme
            )
        }
    }

    @Composable
    private fun FeatureItem(
        icon: ImageVector,
        text: String,
        theme: ThemeColor
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = theme.onContainer,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                fontSize = 13.nonScaledSp,
                color = theme.onContainer
            )
        }
    }
}

@Composable
fun NavbarPosition.toLabel(): String = stringResource(
    when(this) {
        NavbarPosition.TOP -> R.string.settings_position_top
        NavbarPosition.BOTTOM -> R.string.settings_position_bottom
    }
)

@Composable
fun QuickActionPosition.toLabel(): String = stringResource(
    when(this) {
        QuickActionPosition.TOP_LEFT -> R.string.settings_position_top_left
        QuickActionPosition.TOP_RIGHT -> R.string.settings_position_top_right
        QuickActionPosition.BOTTOM_LEFT -> R.string.settings_position_bottom_left
        QuickActionPosition.BOTTOM_RIGHT -> R.string.settings_position_bottom_right
    }
)
