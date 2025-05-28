package com.example.cocktails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    onNavigateToHome: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(16.dp))
        Text(
            "Menu",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Divider()
        NavigationDrawerItem(
            label = { Text("Strona główna") },
            selected = false,
            onClick = onNavigateToHome,
            icon = { Icon(Icons.Default.Home, contentDescription = "Strona główna") }
        )
        NavigationDrawerItem(
            label = { Text("Ulubione") },
            selected = false,
            onClick = onNavigateToFavorites,
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Ulubione") }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onThemeToggle)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ciemny motyw",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeToggle() },
                thumbContent = {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                        contentDescription = if (isDarkTheme) "Ciemny motyw włączony" else "Jasny motyw włączony",
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}
