package com.acu.navigationbasics.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Violet80,
    secondary = Stone80,
    tertiary = Cyan80
)

private val LightColorScheme = lightColorScheme(
    primary = Violet40,
    secondary = Stone40,
    tertiary = Cyan40
)

/**
 * Applies the Material 3 theme used by the navigation sample.
 */
@Composable
fun NavigationBasicsTheme(
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
