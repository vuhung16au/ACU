package com.acu.helloworldkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acu.helloworldkotlin.ui.theme.HelloWorldKotlinTheme

/**
 * Main activity for the first Kotlin and Jetpack Compose example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloWorldKotlinTheme {
                HelloWorldApp()
            }
        }
    }
}

/**
 * Displays the app surface and hosts the main screen content.
 */
@Composable
fun HelloWorldApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HelloWorldScreen()
    }
}

/**
 * Displays a centred greeting and a button with a simple click counter.
 */
@Composable
fun HelloWorldScreen() {
    var clickCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4FF))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreetingTitle()
        CounterSummary(clickCount = clickCount)
        CounterButton(onClick = { clickCount++ })
    }
}

/**
 * Displays the greeting text for the screen.
 */
@Composable
fun GreetingTitle() {
    Text(
        text = GreetingMessageBuilder.buildGreeting(),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

/**
 * Displays the current click count in a short summary.
 *
 * @param clickCount The number of times the learner pressed the button
 */
@Composable
fun CounterSummary(clickCount: Int) {
    Text(
        text = GreetingMessageBuilder.buildCounterMessage(clickCount),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 24.dp)
    )
}

/**
 * Displays the button used to increment the click counter.
 *
 * @param onClick Runs when the user presses the button
 */
@Composable
fun CounterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Tap me")
    }
}

/**
 * Preview for the Hello World screen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun HelloWorldScreenPreview() {
    HelloWorldKotlinTheme {
        HelloWorldApp()
    }
}
