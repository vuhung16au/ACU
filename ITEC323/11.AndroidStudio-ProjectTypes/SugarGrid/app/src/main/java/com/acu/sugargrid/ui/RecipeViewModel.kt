package com.acu.sugargrid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acu.sugargrid.data.MockRecipeDataSource
import com.acu.sugargrid.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the SugarGrid application.
 * Manages the UI state for recipes and provides data to the UI.
 */
class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            try {
                // Simulate some delay if needed, though mock data is instant
                val recipes = MockRecipeDataSource.recipes
                _uiState.value = RecipeUiState.Success(recipes)
            } catch (e: Exception) {
                _uiState.value = RecipeUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

/**
 * Represents the different states of the Recipe UI.
 */
sealed interface RecipeUiState {
    object Loading : RecipeUiState
    data class Success(val recipes: List<Recipe>) : RecipeUiState
    data class Error(val message: String) : RecipeUiState
}
