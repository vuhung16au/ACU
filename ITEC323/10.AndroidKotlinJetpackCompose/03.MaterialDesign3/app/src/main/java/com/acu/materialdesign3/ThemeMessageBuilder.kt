package com.acu.materialdesign3

/**
 * Builds the dashboard cards shown in the Material Design 3 sample.
 */
object ThemeMessageBuilder {
    /**
     * Creates the list of update cards shown on the screen.
     *
     * @param updateCount The number of cards to show
     * @return A list of short card messages
     */
    fun buildUpdateMessages(updateCount: Int): List<String> {
        val baseMessages = listOf(
            "Top app bar keeps the screen organised.",
            "Cards group related content clearly.",
            "Material 3 colors create a cohesive look."
        )

        if (updateCount <= baseMessages.size) {
            return baseMessages.take(updateCount)
        }

        val extraMessages = (baseMessages.size + 1..updateCount).map { index ->
            "New update card $index added from the FAB."
        }

        return baseMessages + extraMessages
    }
}
