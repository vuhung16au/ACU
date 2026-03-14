package com.acu.comprehensiveapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acu.comprehensiveapp.viewmodel.StudentViewModel

/**
 * Displays the add and edit form screen.
 */
@Composable
fun StudentFormScreen(
    studentViewModel: StudentViewModel,
    studentId: Int,
    contentPadding: PaddingValues,
    onNavigateBack: () -> Unit,
    onSaveComplete: () -> Unit
) {
    val existingStudent = studentViewModel.getStudentById(studentId)
    var firstName by remember(existingStudent) { mutableStateOf(existingStudent?.firstName ?: "") }
    var lastName by remember(existingStudent) { mutableStateOf(existingStudent?.lastName ?: "") }
    var email by remember(existingStudent) { mutableStateOf(existingStudent?.email ?: "") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (existingStudent == null) "Add Student" else "Edit Student",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    errorMessage = "All fields are required"
                } else {
                    if (existingStudent == null) {
                        studentViewModel.addStudent(firstName, lastName, email)
                    } else {
                        studentViewModel.updateStudent(studentId, firstName, lastName, email)
                    }
                    onSaveComplete()
                }
            }
        ) {
            Text("Save")
        }
        Button(onClick = onNavigateBack) {
            Text("Cancel")
        }
    }
}
