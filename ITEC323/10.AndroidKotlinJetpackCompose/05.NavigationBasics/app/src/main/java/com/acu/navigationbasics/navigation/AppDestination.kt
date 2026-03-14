package com.acu.navigationbasics.navigation

/**
 * Defines the routes used by the navigation basics example.
 */
object AppDestination {
    const val WelcomeRoute = "welcome"
    const val StudentListRoute = "students"
    const val StudentDetailRoute = "student/{studentId}"

    /**
     * Creates a concrete detail route for a student.
     */
    fun studentDetailRoute(studentId: Int): String {
        return "student/$studentId"
    }

    /**
     * Returns the title shown in the top app bar for a route.
     */
    fun titleForRoute(route: String?): String {
        return when {
            route == WelcomeRoute -> "Welcome"
            route == StudentListRoute -> "Student List"
            route?.startsWith("student/") == true -> "Student Detail"
            else -> "Navigation Basics"
        }
    }
}
