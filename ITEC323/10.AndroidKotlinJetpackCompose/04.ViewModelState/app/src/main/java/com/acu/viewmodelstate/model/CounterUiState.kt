package com.acu.viewmodelstate.model

/**
 * Represents the state shown on the counter screen.
 *
 * @property count The current counter value
 * @property statusMessage A short message describing the last action
 */
data class CounterUiState(
    val count: Int = 0,
    val statusMessage: String = "Ready to count"
)
