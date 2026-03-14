package com.acu.navigationbasics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acu.navigationbasics.model.SampleStudent

/**
 * Displays the student detail screen.
 *
 * @param student The selected student or null when not found
 * @param contentPadding Padding from the scaffold
 * @param onNavigateBack Runs when the user goes back
 */
@Composable
fun StudentDetailScreen(
    student: SampleStudent?,
    contentPadding: PaddingValues,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (student == null) {
            Text(
                text = "Student not found",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text(
                text = student.fullName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Student ID: ${student.id}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Course: ${student.course}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Back")
        }
    }
}
