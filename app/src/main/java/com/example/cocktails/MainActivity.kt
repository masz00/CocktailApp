package com.example.cocktails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cocktails.ui.theme.CocktailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        val savedTheme = sharedPreferences.getBoolean("is_dark_theme", false)

        setContent {
            var isDarkTheme by remember { mutableStateOf(savedTheme) }

            val toggleTheme = {
                isDarkTheme = !isDarkTheme
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_dark_theme", isDarkTheme)
                editor.apply()
            }
            CocktailsTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CocktailApp(
                        onSendSms = { cocktail ->
                            Toast.makeText(
                                this,
                                "Wysyłanie SMS z przepisem na ${cocktail.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = toggleTheme
                    )
                }
            }
        }
    }
}