package com.acu.comprehensiveapp

import com.acu.comprehensiveapp.viewmodel.StudentViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Unit tests for the student ViewModel.
 */
class StudentViewModelTest {
    @Test
    fun addStudent_ValidData_IncreasesListSize() {
        val viewModel = StudentViewModel()
        val initialSize = viewModel.students.value.size

        viewModel.addStudent("Mia", "Brown", "mia.brown@acu.edu")

        assertEquals(initialSize + 1, viewModel.students.value.size)
    }

    @Test
    fun updateStudent_ExistingStudent_ChangesStoredValues() {
        val viewModel = StudentViewModel()

        viewModel.updateStudent(1, "Ava", "Nguyen", "ava.updated@acu.edu")

        assertEquals("ava.updated@acu.edu", viewModel.getStudentById(1)?.email)
    }

    @Test
    fun deleteStudent_ExistingStudent_RemovesStudent() {
        val viewModel = StudentViewModel()

        viewModel.deleteStudent(1)

        assertEquals(null, viewModel.getStudentById(1))
        assertNotNull(viewModel.getStudentById(2))
    }
}
