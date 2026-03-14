package com.acu.comprehensiveapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Rose80,
    secondary = Olive80,
    tertiary = Sky80
)

private val LightColorScheme = lightColorScheme(
    primary = Rose40,
    secondary = Olive40,
    tertiary = Sky40
)

/**
 * Applies the Material 3 theme used by the comprehensive app.
 */
@Composable
fun ComprehensiveAppTheme(
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
