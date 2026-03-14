package com.acu.navigationbasics.model

/**
 * Provides sample students for the navigation example.
 */
object SampleStudentRepository {
    private val students = listOf(
        SampleStudent(1, "Ava", "Nguyen", "Mobile Development"),
        SampleStudent(2, "Noah", "Smith", "Cloud Computing"),
        SampleStudent(3, "Mia", "Brown", "Cyber Security")
    )

    /**
     * Returns all sample students.
     */
    fun getStudents(): List<SampleStudent> = students

    /**
     * Returns one student by ID or null when not found.
     */
    fun getStudentById(studentId: Int): SampleStudent? {
        return students.find { it.id == studentId }
    }
}
