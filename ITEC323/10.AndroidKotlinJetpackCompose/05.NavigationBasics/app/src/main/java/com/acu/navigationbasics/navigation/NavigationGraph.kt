package com.acu.navigationbasics.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.acu.navigationbasics.model.SampleStudentRepository
import com.acu.navigationbasics.ui.screens.StudentDetailScreen
import com.acu.navigationbasics.ui.screens.StudentListScreen
import com.acu.navigationbasics.ui.screens.WelcomeScreen

/**
 * Defines the screens and routes used by the app.
 *
 * @param navController The app nav controller
 * @param modifier Optional modifier for the host
 * @param contentPadding Padding from the scaffold
 */
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.WelcomeRoute,
        modifier = modifier
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
                students = SampleStudentRepository.getStudents(),
                contentPadding = contentPadding,
                onNavigateToDetail = { studentId ->
                    navController.navigate(AppDestination.studentDetailRoute(studentId))
                }
            )
        }
        composable(
            route = AppDestination.StudentDetailRoute,
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: 0
            val student = SampleStudentRepository.getStudentById(studentId)
            StudentDetailScreen(
                student = student,
                contentPadding = contentPadding,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
