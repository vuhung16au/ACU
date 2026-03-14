package com.acu.comprehensiveapp.model

/**
 * Represents a student profile in the comprehensive app.
 *
 * @property id The student identifier
 * @property firstName The student's first name
 * @property lastName The student's last name
 * @property email The student's email address
 */
data class StudentProfile(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
) {
    /**
     * Returns the student's full name.
     */
    val fullName: String
        get() = "$firstName $lastName"
}
