package com.acu.composebasics

/**
 * Builds the preview lines shown under the Compose Basics form.
 */
object FormPreviewBuilder {
    /**
     * Creates the preview lines for the current form state.
     *
     * @param studentName The name entered by the learner
     * @param isEnrolled Whether the learner marked the checkbox
     * @param wantsReminders Whether the learner enabled reminders
     * @return A list of short preview lines for the LazyColumn
     */
    fun buildPreviewItems(
        studentName: String,
        isEnrolled: Boolean,
        wantsReminders: Boolean
    ): List<String> {
        val displayName = if (studentName.isBlank()) "Not entered yet" else studentName.trim()
        val enrolledText = if (isEnrolled) "Yes" else "No"
        val remindersText = if (wantsReminders) "On" else "Off"

        return listOf(
            "Student name: $displayName",
            "Enrolled in lab: $enrolledText",
            "Study reminders: $remindersText"
        )
    }
}
