package com.acu.comprehensiveapp.viewmodel

import androidx.lifecycle.ViewModel
import com.acu.comprehensiveapp.model.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Stores and updates the student list for the comprehensive app.
 */
class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow(
        listOf(
            StudentProfile(1, "Ava", "Nguyen", "ava.nguyen@acu.edu"),
            StudentProfile(2, "Noah", "Smith", "noah.smith@acu.edu")
        )
    )
    private var nextStudentId = 3

    /**
     * Exposes the student list to the UI.
     */
    val students: StateFlow<List<StudentProfile>> = _students.asStateFlow()

    /**
     * Adds a new student.
     */
    fun addStudent(firstName: String, lastName: String, email: String) {
        val newStudent = StudentProfile(
            id = nextStudentId++,
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = email.trim()
        )
        _students.value = _students.value + newStudent
    }

    /**
     * Updates an existing student.
     */
    fun updateStudent(studentId: Int, firstName: String, lastName: String, email: String) {
        _students.value = _students.value.map { student ->
            if (student.id == studentId) {
                student.copy(
                    firstName = firstName.trim(),
                    lastName = lastName.trim(),
                    email = email.trim()
                )
            } else {
                student
            }
        }
    }

    /**
     * Deletes a student by ID.
     */
    fun deleteStudent(studentId: Int) {
        _students.value = _students.value.filterNot { it.id == studentId }
    }

    /**
     * Returns one student by ID or null when not found.
     */
    fun getStudentById(studentId: Int): StudentProfile? {
        return _students.value.find { it.id == studentId }
    }
}
