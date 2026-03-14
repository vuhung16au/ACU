package com.acu.navigationbasics.model

/**
 * Represents a simple student used in the navigation example.
 *
 * @property id The student identifier
 * @property firstName The student's first name
 * @property lastName The student's last name
 * @property course The student's course name
 */
data class SampleStudent(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val course: String
) {
    /**
     * Returns the student's full name.
     */
    val fullName: String
        get() = "$firstName $lastName"
}
