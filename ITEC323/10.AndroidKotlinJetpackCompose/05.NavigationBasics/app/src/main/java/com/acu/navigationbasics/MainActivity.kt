package com.acu.navigationbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.acu.navigationbasics.navigation.AppDestination
import com.acu.navigationbasics.navigation.NavigationGraph
import com.acu.navigationbasics.ui.theme.NavigationBasicsTheme

/**
 * Main activity for the navigation basics example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationBasicsApp()
        }
    }
}

/**
 * Hosts the app theme and scaffold for navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBasicsApp() {
    NavigationBasicsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            val title = AppDestination.titleForRoute(currentRoute)

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(title) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            ) { innerPadding ->
                NavigationGraph(
                    navController = navController,
                    modifier = Modifier,
                    contentPadding = innerPadding
                )
            }
        }
    }
}
