package com.olr261dn.ui.compose.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.olr261dn.ui.theme.customTheme


@Composable
fun ItemCardLayout(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .padding(bottom = 10.dp, top = 10.dp, start = 5.dp, end = 5.dp)
            .wrapContentWidth()
            .fillMaxWidth()
            .shadow(
                elevation = if (isSelected) 12.dp else 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = if (isSelected) customTheme().primary.copy(alpha = 0.4f)
                else customTheme().onSurface.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (isSelected) 3.dp else 2.dp,
            brush = Brush.linearGradient(
                colors = if (isSelected) {
                    listOf(
                        customTheme().primary,
                        customTheme().secondary,
                        customTheme().tertiary,
                        customTheme().container
                    )
                } else {
                    listOf(
                        customTheme().onSurface.copy(alpha = 0.5f),
                        customTheme().surface.copy(alpha = 0.5f)
                    )
                },
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        ),
        colors = CardDefaults.cardColors(
            containerColor = customTheme().secondary.copy(0.7f),
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            customTheme().surface
                        )
                    )
                )
        ) {
            content()
        }
    }

}
