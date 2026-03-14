package com.acu.viewmodelstate.viewmodel

import androidx.lifecycle.ViewModel
import com.acu.viewmodelstate.model.CounterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Stores and updates the counter state for the screen.
 */
class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())

    /**
     * Exposes read-only screen state to the UI.
     */
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    /**
     * Increases the counter by one.
     */
    fun increment() {
        val newCount = _uiState.value.count + 1
        _uiState.value = CounterUiState(
            count = newCount,
            statusMessage = "Increment pressed"
        )
    }

    /**
     * Decreases the counter by one.
     */
    fun decrement() {
        val newCount = _uiState.value.count - 1
        _uiState.value = CounterUiState(
            count = newCount,
            statusMessage = "Decrement pressed"
        )
    }

    /**
     * Resets the counter back to zero.
     */
    fun reset() {
        _uiState.value = CounterUiState(
            count = 0,
            statusMessage = "Counter reset"
        )
    }
}
