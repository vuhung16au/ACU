package com.acu.composebasics.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Sky80,
    secondary = Slate80,
    tertiary = Green80
)

private val LightColorScheme = lightColorScheme(
    primary = Sky40,
    secondary = Slate40,
    tertiary = Green40
)

/**
 * Applies the Material 3 theme used by the Hello World sample.
 *
 * @param darkTheme True when the system is using dark mode
 * @param content The UI shown inside the theme
 */
@Composable
fun ComposeBasicsTheme(
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
