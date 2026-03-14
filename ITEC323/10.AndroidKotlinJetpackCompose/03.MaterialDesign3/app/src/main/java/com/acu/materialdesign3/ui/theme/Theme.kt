package com.acu.materialdesign3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Coral80,
    secondary = Sand80,
    tertiary = Teal80
)

private val LightColorScheme = lightColorScheme(
    primary = Coral40,
    secondary = Sand40,
    tertiary = Teal40
)

/**
 * Applies the Material 3 theme used by the sample.
 *
 * @param darkTheme True when dark mode is enabled
 * @param content The UI shown inside the theme
 */
@Composable
fun MaterialDesign3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
