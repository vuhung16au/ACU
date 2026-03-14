package com.acu.materialdesign3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acu.materialdesign3.ui.theme.MaterialDesign3Theme

/**
 * Main activity for the Material Design 3 example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialDesign3App()
        }
    }
}

/**
 * Hosts the app theme and root screen.
 */
@Composable
fun MaterialDesign3App() {
    var darkThemeEnabled by remember { mutableStateOf(false) }

    MaterialDesign3Theme(darkTheme = darkThemeEnabled) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MaterialDesign3Screen(
                darkThemeEnabled = darkThemeEnabled,
                onThemeChanged = { darkThemeEnabled = it }
            )
        }
    }
}

/**
 * Displays a Material 3 dashboard using scaffold, cards, and a FAB.
 *
 * @param darkThemeEnabled Current theme state
 * @param onThemeChanged Runs when the theme switch changes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialDesign3Screen(
    darkThemeEnabled: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    var cardCount by remember { mutableIntStateOf(3) }
    val updateMessages = ThemeMessageBuilder.buildUpdateMessages(cardCount)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Material Design 3") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { cardCount++ },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add update card"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ThemeToggleCard(
                    darkThemeEnabled = darkThemeEnabled,
                    onThemeChanged = onThemeChanged
                )
            }
            items(updateMessages) { message ->
                UpdateCard(message = message)
            }
        }
    }
}

/**
 * Displays a card with the theme toggle switch.
 *
 * @param darkThemeEnabled Current theme state
 * @param onThemeChanged Runs when the theme switch changes
 */
@Composable
fun ThemeToggleCard(
    darkThemeEnabled: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dark mode",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Switch(
                checked = darkThemeEnabled,
                onCheckedChange = onThemeChanged
            )
        }
    }
}

/**
 * Displays one dashboard card.
 *
 * @param message The message shown inside the card
 */
@Composable
fun UpdateCard(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(20.dp)
        )
    }
}

/**
 * Preview for the Material Design 3 screen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun MaterialDesign3ScreenPreview() {
    MaterialDesign3App()
}
