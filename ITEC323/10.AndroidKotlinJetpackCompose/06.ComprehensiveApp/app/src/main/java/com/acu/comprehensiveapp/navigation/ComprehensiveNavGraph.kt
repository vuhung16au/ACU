package com.acu.comprehensiveapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.acu.comprehensiveapp.ui.screens.StudentDetailScreen
import com.acu.comprehensiveapp.ui.screens.StudentFormScreen
import com.acu.comprehensiveapp.ui.screens.StudentListScreen
import com.acu.comprehensiveapp.ui.screens.WelcomeScreen
import com.acu.comprehensiveapp.viewmodel.StudentViewModel

/**
 * Defines the screens and routes for the comprehensive app.
 */
@Composable
fun ComprehensiveNavGraph(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.WelcomeRoute
    ) {
        composable(AppDestination.WelcomeRoute) {
            WelcomeScreen(
                contentPadding = contentPadding,
                onNavigateToStudents = {
                    navController.navigate(AppDestination.StudentListRoute)
                }
            )
        }
        composable(AppDestination.StudentListRoute) {
            StudentListScreen(
                studentViewModel = studentViewModel,
                contentPadding = contentPadding,
                onNavigateToDetail = { studentId ->
                    navController.navigate(AppDestination.studentDetailRoute(studentId))
                },
                onNavigateToAdd = {
                    navController.navigate(AppDestination.studentFormRoute())
                }
            )
        }
        composable(
            route = AppDestination.StudentDetailRoute,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: -1
            StudentDetailScreen(
                studentViewModel = studentViewModel,
                studentId = studentId,
                contentPadding = contentPadding,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id ->
                    navController.navigate(AppDestination.studentFormRoute(id))
                },
                onDeleteAndReturn = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = AppDestination.StudentFormRoute,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: -1
            StudentFormScreen(
                studentViewModel = studentViewModel,
                studentId = studentId,
                contentPadding = contentPadding,
                onNavigateBack = { navController.popBackStack() },
                onSaveComplete = {
                    navController.popBackStack()
                }
            )
        }
    }
}
