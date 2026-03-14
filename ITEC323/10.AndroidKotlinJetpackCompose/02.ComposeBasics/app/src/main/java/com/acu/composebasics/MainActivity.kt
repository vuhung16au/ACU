package com.acu.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acu.composebasics.ui.theme.ComposeBasicsTheme

/**
 * Main activity for the Compose Basics example.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBasicsTheme {
                ComposeBasicsApp()
            }
        }
    }
}

/**
 * Displays the app surface and hosts the main screen content.
 */
@Composable
fun ComposeBasicsApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ComposeBasicsScreen()
    }
}

/**
 * Displays a form with basic Compose input controls and a live preview list.
 */
@Composable
fun ComposeBasicsScreen() {
    var studentName by remember { mutableStateOf("") }
    var isEnrolled by remember { mutableStateOf(false) }
    var wantsReminders by remember { mutableStateOf(true) }
    val previewItems = FormPreviewBuilder.buildPreviewItems(
        studentName = studentName,
        isEnrolled = isEnrolled,
        wantsReminders = wantsReminders
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F8FF))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenHeading()
        }
        item {
            NameField(
                studentName = studentName,
                onNameChange = { studentName = it }
            )
        }
        item {
            EnrolledCheckbox(
                isEnrolled = isEnrolled,
                onCheckedChange = { isEnrolled = it }
            )
        }
        item {
            ReminderSwitch(
                wantsReminders = wantsReminders,
                onCheckedChange = { wantsReminders = it }
            )
        }
        item {
            PreviewHeading()
        }
        items(previewItems) { item ->
            PreviewCard(previewText = item)
        }
    }
}

/**
 * Displays the title at the top of the screen.
 */
@Composable
fun ScreenHeading() {
    Text(
        text = "Compose Basics Form",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 12.dp)
    )
}

/**
 * Displays the text field for the learner name.
 *
 * @param studentName The current name value
 * @param onNameChange Runs when the text field changes
 */
@Composable
fun NameField(studentName: String, onNameChange: (String) -> Unit) {
    TextField(
        value = studentName,
        onValueChange = onNameChange,
        label = { Text("Student name") },
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Displays the enrolment checkbox row.
 *
 * @param isEnrolled The current checkbox state
 * @param onCheckedChange Runs when the checkbox changes
 */
@Composable
fun EnrolledCheckbox(isEnrolled: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Enrolled in lab")
        Checkbox(
            checked = isEnrolled,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * Displays the reminder switch row.
 *
 * @param wantsReminders The current switch state
 * @param onCheckedChange Runs when the switch changes
 */
@Composable
fun ReminderSwitch(wantsReminders: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Enable reminders")
        Switch(
            checked = wantsReminders,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * Displays the heading for the preview section.
 */
@Composable
fun PreviewHeading() {
    Text(
        text = "Live Preview",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

/**
 * Displays one preview line inside a card.
 *
 * @param previewText The preview text to display
 */
@Composable
fun PreviewCard(previewText: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = previewText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview for the Compose Basics screen in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun ComposeBasicsScreenPreview() {
    ComposeBasicsTheme {
        ComposeBasicsApp()
    }
}
