package com.acu.helloworldkotlin

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the greeting message helper.
 */
class GreetingMessageBuilderTest {
    @Test
    fun buildGreeting_DefaultMessage_ReturnsAndroid16Greeting() {
        val result = GreetingMessageBuilder.buildGreeting()

        assertEquals("Hello Android 16!", result)
    }

    @Test
    fun buildCounterMessage_SingleClick_UsesSingularLabel() {
        val result = GreetingMessageBuilder.buildCounterMessage(1)

        assertEquals("Button pressed 1 time", result)
    }

    @Test
    fun buildCounterMessage_ManyClicks_UsesPluralLabel() {
        val result = GreetingMessageBuilder.buildCounterMessage(3)

        assertEquals("Button pressed 3 times", result)
    }
}
