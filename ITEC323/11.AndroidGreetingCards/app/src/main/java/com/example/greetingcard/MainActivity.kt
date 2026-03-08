package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme

/**
 * Main entry point for the Greeting Card app.
 *
 * In Android, [onCreate] is the equivalent of a "main" function—it runs when the activity
 * is first created. [setContent] defines the UI using Jetpack Compose composable functions.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                // Outer Surface fills the screen with the theme background colour
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting displays the personalised introduction (default: "Android")
                    Greeting("Android")
                }
            }
        }
    }
}

/**
 * A composable that displays a greeting with the given name.
 *
 * Composable functions build UI in Jetpack Compose. Key rules:
 * - Annotate with [@Composable]
 * - Use PascalCase for the function name
 * - They do not return a value (they describe UI, not compute it)
 *
 * @param name The name to show in the greeting (e.g. "Hi, my name is [name]!")
 * @param modifier Optional [Modifier] to adjust layout or behaviour (e.g. padding); should be
 *        the first optional parameter in composables
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Surface is a container that lets us set background colour and other appearance
    Surface(color = Color.Cyan) {
        Text(
            text = "Hi, my name is $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}

/**
 * Preview for the [Greeting] composable in Android Studio's Design view.
 *
 * [@Preview] lets you see the composable without running the app on a device or emulator.
 * Use Build & Refresh in the Design pane to update the preview.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        Greeting("Meghan")
    }
}
