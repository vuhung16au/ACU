package com.acu.comprehensiveapp

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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acu.comprehensiveapp.navigation.AppDestination
import com.acu.comprehensiveapp.navigation.ComprehensiveNavGraph
import com.acu.comprehensiveapp.ui.theme.ComprehensiveAppTheme
import com.acu.comprehensiveapp.viewmodel.StudentViewModel

/**
 * Main activity for the comprehensive app example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComprehensiveAppRoot()
        }
    }
}

/**
 * Hosts the app theme, top bar, and navigation graph.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveAppRoot() {
    ComprehensiveAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val studentViewModel: StudentViewModel = viewModel()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(AppDestination.titleForRoute(currentRoute)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            ) { innerPadding ->
                ComprehensiveNavGraph(
                    navController = navController,
                    studentViewModel = studentViewModel,
                    contentPadding = innerPadding
                )
            }
        }
    }
}
