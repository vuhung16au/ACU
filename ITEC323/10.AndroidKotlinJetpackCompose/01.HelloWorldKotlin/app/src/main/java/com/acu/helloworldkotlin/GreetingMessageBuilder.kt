package com.acu.helloworldkotlin

/**
 * Builds the small text messages shown in the Hello World screen.
 */
object GreetingMessageBuilder {
    /**
     * Creates the main greeting for the screen.
     *
     * @return The greeting shown to the learner
     */
    fun buildGreeting(): String {
        return "Hello Android 16!"
    }

    /**
     * Creates the counter summary for the button click total.
     *
     * @param clickCount The number of button presses
     * @return A short sentence describing the current total
     */
    fun buildCounterMessage(clickCount: Int): String {
        return if (clickCount == 1) {
            "Button pressed 1 time"
        } else {
            "Button pressed $clickCount times"
        }
    }
}
