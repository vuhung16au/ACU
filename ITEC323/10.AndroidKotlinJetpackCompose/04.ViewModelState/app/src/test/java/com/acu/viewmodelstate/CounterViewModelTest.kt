package com.acu.viewmodelstate

import com.acu.viewmodelstate.viewmodel.CounterViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the counter ViewModel.
 */
class CounterViewModelTest {
    @Test
    fun increment_DefaultState_IncreasesCounter() {
        val viewModel = CounterViewModel()

        viewModel.increment()

        assertEquals(1, viewModel.uiState.value.count)
        assertEquals("Increment pressed", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun decrement_DefaultState_DecreasesCounter() {
        val viewModel = CounterViewModel()

        viewModel.decrement()

        assertEquals(-1, viewModel.uiState.value.count)
        assertEquals("Decrement pressed", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun reset_AfterChanges_ReturnsToZero() {
        val viewModel = CounterViewModel()
        viewModel.increment()
        viewModel.increment()

        viewModel.reset()

        assertEquals(0, viewModel.uiState.value.count)
        assertEquals("Counter reset", viewModel.uiState.value.statusMessage)
    }
}
