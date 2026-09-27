package com.example.adivan.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AdivanColorScheme = lightColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = SoftGreen,
    onPrimaryContainer = DarkGreenVariant,
    secondary = SoftGreenAccent,
    onSecondary = DarkGreenVariant,
    tertiary = CreamDark,
    background = Color.White,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    surfaceVariant = SoftGreen,
    onSurfaceVariant = TextSecondary,
    error = WarningOrange,
)

@Composable
fun AdivanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AdivanColorScheme,
        typography = Typography,
        content = content
    )
}
