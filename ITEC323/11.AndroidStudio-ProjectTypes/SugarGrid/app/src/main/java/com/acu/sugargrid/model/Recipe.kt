package com.acu.sugargrid.model

/**
 * Data model for a dessert recipe.
 *
 * @property id Unique identifier for the recipe.
 * @property title The name of the dessert.
 * @property imageUrl URL or resource path for the recipe image.
 * @property ingredients List of ingredients required (for phase 2).
 * @property instructions Step-by-step cooking instructions (for phase 2).
 */
data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList()
)
