package com.acu.composebasics

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the form preview helper.
 */
class FormPreviewBuilderTest {
    @Test
    fun buildPreviewItems_BlankName_UsesFallbackText() {
        val result = FormPreviewBuilder.buildPreviewItems("", false, true)

        assertEquals("Student name: Not entered yet", result[0])
    }

    @Test
    fun buildPreviewItems_CheckedControls_ReturnsReadableSummary() {
        val result = FormPreviewBuilder.buildPreviewItems("Ava", true, false)

        assertEquals("Student name: Ava", result[0])
        assertEquals("Enrolled in lab: Yes", result[1])
        assertEquals("Study reminders: Off", result[2])
    }
}
