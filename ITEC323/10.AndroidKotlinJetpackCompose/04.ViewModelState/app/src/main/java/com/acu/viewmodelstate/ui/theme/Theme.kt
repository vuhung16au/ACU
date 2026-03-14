package com.acu.viewmodelstate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Indigo80,
    secondary = Steel80,
    tertiary = Aqua80
)

private val LightColorScheme = lightColorScheme(
    primary = Indigo40,
    secondary = Steel40,
    tertiary = Aqua40
)

/**
 * Applies the Material 3 theme used by the ViewModel sample.
 *
 * @param darkTheme True when the system is using dark mode
 * @param content The UI shown inside the theme
 */
@Composable
fun ViewModelStateTheme(
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
