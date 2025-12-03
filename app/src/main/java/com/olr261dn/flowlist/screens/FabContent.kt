package com.olr261dn.flowlist.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.olr261dn.domain.model.QuickActionPosition
import com.olr261dn.resources.R
import com.olr261dn.ui.theme.customTheme


@Composable
internal fun FabContent(
    quickActionPosition: QuickActionPosition,
    onAddTap: () -> Unit,
    onStatisticsTap: () -> Unit,
    onSettings: () -> Unit,
) {
    var expanded by rememberSaveable(quickActionPosition) { mutableStateOf(false) }

    val boxAlignment = when (quickActionPosition) {
        QuickActionPosition.TOP_RIGHT -> Alignment.TopEnd
        QuickActionPosition.TOP_LEFT -> Alignment.TopStart
        QuickActionPosition.BOTTOM_RIGHT -> Alignment.BottomEnd
        QuickActionPosition.BOTTOM_LEFT -> Alignment.BottomStart
    }

    val horizontalAlignment = when (quickActionPosition) {
        QuickActionPosition.TOP_RIGHT, QuickActionPosition.BOTTOM_RIGHT -> Alignment.End
        QuickActionPosition.TOP_LEFT, QuickActionPosition.BOTTOM_LEFT -> Alignment.Start
    }

    val expandAnimation = when (quickActionPosition) {
        QuickActionPosition.TOP_RIGHT, QuickActionPosition.TOP_LEFT -> fadeIn() + expandVertically(
            expandFrom = Alignment.Top
        )

        QuickActionPosition.BOTTOM_RIGHT, QuickActionPosition.BOTTOM_LEFT -> fadeIn() + expandVertically(
            expandFrom = Alignment.Bottom
        )
    }

    val shrinkAnimation = when (quickActionPosition) {
        QuickActionPosition.TOP_RIGHT, QuickActionPosition.TOP_LEFT -> fadeOut() + shrinkVertically(
            shrinkTowards = Alignment.Top
        )

        QuickActionPosition.BOTTOM_RIGHT, QuickActionPosition.BOTTOM_LEFT -> fadeOut() + shrinkVertically(
            shrinkTowards = Alignment.Bottom
        )
    }

    Box(
        contentAlignment = boxAlignment,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (quickActionPosition == QuickActionPosition.TOP_RIGHT || quickActionPosition == QuickActionPosition.TOP_LEFT) {
                MainFab(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandAnimation,
                exit = shrinkAnimation
            ) {
                Column(
                    horizontalAlignment = horizontalAlignment,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FabMenuItem(
                        onClick = {
                            onSettings()
                            expanded = false
                        },
                        painter = painterResource(R.drawable.round_settings_24),
                        contentDescription = stringResource(R.string.settings_navigation)
                    )

                    FabMenuItem(
                        onClick = {
                            onStatisticsTap()
                            expanded = false
                        },
                        painter = painterResource(R.drawable.outline_analytics_24),
                        contentDescription = stringResource(R.string.analytics_button_fab)
                    )

                    FabMenuItem(
                        onClick = {
                            onAddTap()
                            expanded = false
                        },
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.fab_content_description)
                    )
                }
            }

            if (quickActionPosition == QuickActionPosition.BOTTOM_RIGHT || quickActionPosition == QuickActionPosition.BOTTOM_LEFT) {
                MainFab(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
        }
    }
}

@Composable
private fun FabMenuItem(
    onClick: () -> Unit,
    contentDescription: String,
    painter: Painter? = null,
    imageVector: ImageVector? = null,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        containerColor = customTheme().container,
        contentColor = customTheme().onContainer
    ) {
        when {
            painter != null -> Icon(
                modifier = Modifier.fillMaxSize(0.8f),
                painter = painter,
                contentDescription = contentDescription
            )

            imageVector != null -> Icon(
                modifier = Modifier.fillMaxSize(0.8f),
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}

@Composable
private fun MainFab(
    expanded: Boolean,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(43.dp),
        shape = CircleShape,
        containerColor = customTheme().primary,
        contentColor = customTheme().onPrimary
    ) {
        Icon(
            imageVector = if (expanded) Icons.Default.Close else Icons.Default.Menu,
            contentDescription = stringResource(R.string.speed_dial_fab),
            modifier = Modifier
                .graphicsLayer { rotationZ = if (expanded) 180f else 0f }
                .fillMaxSize(0.8f)
        )
    }
}