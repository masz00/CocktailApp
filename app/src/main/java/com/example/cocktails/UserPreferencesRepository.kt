package com.example.cocktails // lub com.example.cocktails.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val FAVORITE_COCKTAIL_NAMES = stringSetPreferencesKey("favorite_cocktail_names")
    }

    val favoriteCocktailNamesFlow: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.FAVORITE_COCKTAIL_NAMES] ?: emptySet()
        }

    suspend fun addFavorite(cocktailName: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_COCKTAIL_NAMES] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_COCKTAIL_NAMES] = currentFavorites + cocktailName
        }
    }

    suspend fun removeFavorite(cocktailName: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_COCKTAIL_NAMES] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_COCKTAIL_NAMES] = currentFavorites - cocktailName
        }
    }
}