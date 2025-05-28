
package com.example.cocktails

import java.io.Serializable

data class Cocktail(
    val name: String,
    val imageResId: Int,
    val ingredients: List<String>,
    val instructions: String,
    val description: String,
    val isFavorite: Boolean = false,
    val isAlcoholic: Boolean
) : Serializable
