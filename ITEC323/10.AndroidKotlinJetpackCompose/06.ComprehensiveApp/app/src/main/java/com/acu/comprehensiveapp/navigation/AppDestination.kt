package com.acu.comprehensiveapp.navigation

/**
 * Defines the routes used by the comprehensive app.
 */
object AppDestination {
    const val WelcomeRoute = "welcome"
    const val StudentListRoute = "students"
    const val StudentDetailRoute = "student/{studentId}"
    const val StudentFormRoute = "studentForm/{studentId}"

    /**
     * Creates a detail route for the given student.
     */
    fun studentDetailRoute(studentId: Int): String {
        return "student/$studentId"
    }

    /**
     * Creates a form route for adding or editing a student.
     */
    fun studentFormRoute(studentId: Int = -1): String {
        return "studentForm/$studentId"
    }

    /**
     * Returns the title shown in the top app bar.
     */
    fun titleForRoute(route: String?): String {
        return when {
            route == WelcomeRoute -> "Welcome"
            route == StudentListRoute -> "Student List"
            route?.startsWith("studentForm/") == true -> "Student Form"
            route?.startsWith("student/") == true -> "Student Detail"
            else -> "Comprehensive App"
        }
    }
}
