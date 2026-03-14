package com.acu.viewmodelstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acu.viewmodelstate.model.CounterUiState
import com.acu.viewmodelstate.ui.theme.ViewModelStateTheme
import com.acu.viewmodelstate.viewmodel.CounterViewModel

/**
 * Main activity for the ViewModel state example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelStateApp()
        }
    }
}

/**
 * Hosts the app theme and main screen.
 */
@Composable
fun ViewModelStateApp() {
    ViewModelStateTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CounterRoute()
        }
    }
}

/**
 * Connects the ViewModel state to the screen.
 *
 * @param counterViewModel The screen ViewModel
 */
@Composable
fun CounterRoute(counterViewModel: CounterViewModel = viewModel()) {
    val uiState by counterViewModel.uiState.collectAsStateWithLifecycle()

    CounterScreen(
        uiState = uiState,
        onIncrement = { counterViewModel.increment() },
        onDecrement = { counterViewModel.decrement() },
        onReset = { counterViewModel.reset() }
    )
}

/**
 * Displays the counter screen and its actions.
 *
 * @param uiState The current screen state
 * @param onIncrement Runs when the increment button is pressed
 * @param onDecrement Runs when the decrement button is pressed
 * @param onReset Runs when the reset button is pressed
 */
@Composable
fun CounterScreen(
    uiState: CounterUiState,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ViewModel Counter",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CounterCard(uiState = uiState)
        CounterActions(
            onIncrement = onIncrement,
            onDecrement = onDecrement,
            onReset = onReset
        )
    }
}

/**
 * Displays the current state inside a card.
 *
 * @param uiState The state to display
 */
@Composable
fun CounterCard(uiState: CounterUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Current count: ${uiState.count}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = uiState.statusMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

/**
 * Displays the action buttons for the counter.
 *
 * @param onIncrement Runs when the increment button is pressed
 * @param onDecrement Runs when the decrement button is pressed
 * @param onReset Runs when the reset button is pressed
 */
@Composable
fun CounterActions(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onDecrement,
            modifier = Modifier.weight(1f)
        ) {
            Text("Decrement")
        }
        Button(
            onClick = onIncrement,
            modifier = Modifier.weight(1f)
        ) {
            Text("Increment")
        }
    }
    Button(
        onClick = onReset,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text("Reset")
    }
}

/**
 * Preview for the counter screen.
 */
@Preview(showBackground = true)
@Composable
fun CounterScreenPreview() {
    ViewModelStateTheme {
        CounterScreen(
            uiState = CounterUiState(count = 3, statusMessage = "Increment pressed"),
            onIncrement = {},
            onDecrement = {},
            onReset = {}
        )
    }
}
