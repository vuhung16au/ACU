package com.acu.materialdesign3

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the Material Design 3 card helper.
 */
class ThemeMessageBuilderTest {
    @Test
    fun buildUpdateMessages_ThreeCards_ReturnsDefaultMessages() {
        val result = ThemeMessageBuilder.buildUpdateMessages(3)

        assertEquals(3, result.size)
        assertEquals("Top app bar keeps the screen organised.", result[0])
    }

    @Test
    fun buildUpdateMessages_FiveCards_AppendsFabMessages() {
        val result = ThemeMessageBuilder.buildUpdateMessages(5)

        assertEquals("New update card 4 added from the FAB.", result[3])
        assertEquals("New update card 5 added from the FAB.", result[4])
    }
}
