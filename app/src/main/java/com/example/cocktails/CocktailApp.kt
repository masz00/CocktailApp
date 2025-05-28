package com.example.cocktails

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

fun getCocktails(context: Context): List<Cocktail> {
    return listOf(
        Cocktail(
            name = context.getString(R.string.cocktail_name_mojito),
            imageResId = R.drawable.mojito,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_mojito).toList(),
            instructions = context.getString(R.string.cocktail_instr_mojito),
            description = context.getString(R.string.cocktail_desc_mojito),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_margarita),
            imageResId = R.drawable.margarita,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_margarita).toList(),
            instructions = context.getString(R.string.cocktail_instr_margarita),
            description = context.getString(R.string.cocktail_desc_margarita),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_pina_colada),
            imageResId = R.drawable.pina_colada,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_pina_colada).toList(),
            instructions = context.getString(R.string.cocktail_instr_pina_colada),
            description = context.getString(R.string.cocktail_desc_pina_colada),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_caipirinha),
            imageResId = R.drawable.caipirinha,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_caipirinha).toList(),
            instructions = context.getString(R.string.cocktail_instr_caipirinha),
            description = context.getString(R.string.cocktail_desc_caipirinha),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_daiquiri),
            imageResId = R.drawable.daiquiri,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_daiquiri).toList(),
            instructions = context.getString(R.string.cocktail_instr_daiquiri),
            description = context.getString(R.string.cocktail_desc_daiquiri),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_old_fashioned),
            imageResId = R.drawable.old_fashioned,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_old_fashioned).toList(),
            instructions = context.getString(R.string.cocktail_instr_old_fashioned),
            description = context.getString(R.string.cocktail_desc_old_fashioned),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_cosmopolitan),
            imageResId = R.drawable.cosmopolitan,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_cosmopolitan).toList(),
            instructions = context.getString(R.string.cocktail_instr_cosmopolitan),
            description = context.getString(R.string.cocktail_desc_cosmopolitan),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_manhattan),
            imageResId = R.drawable.manhattan,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_manhattan).toList(),
            instructions = context.getString(R.string.cocktail_instr_manhattan),
            description = context.getString(R.string.cocktail_desc_manhattan),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_negroni),
            imageResId = R.drawable.negroni,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_negroni).toList(),
            instructions = context.getString(R.string.cocktail_instr_negroni),
            description = context.getString(R.string.cocktail_desc_negroni),
            isAlcoholic = true
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_whiskey_sour),
            imageResId = R.drawable.whiskey_sour,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_whiskey_sour).toList(),
            instructions = context.getString(R.string.cocktail_instr_whiskey_sour),
            description = context.getString(R.string.cocktail_desc_whiskey_sour),
            isAlcoholic = false
        ),
        Cocktail(
            name = context.getString(R.string.cocktail_name_water_with_mint),
            imageResId = R.drawable.water_with_mint,
            ingredients = context.resources.getStringArray(R.array.cocktail_ingredients_water_with_mint).toList(),
            instructions = context.getString(R.string.cocktail_instr_water_with_mint),
            description = context.getString(R.string.cocktail_desc_water_with_mint),
            isAlcoholic = false
        )
    )
}


enum class Screen {
    Home,
    Favorites,

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CocktailApp(
    onSendSms: (Cocktail) -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val userPreferencesRepository = remember { UserPreferencesRepository(context) }

    var allCocktails by remember { mutableStateOf<List<Cocktail>>(emptyList()) }


    LaunchedEffect(Unit) {
        val initialCocktailsFromSource = getCocktails(context)
        val initialFavoriteNames = userPreferencesRepository.favoriteCocktailNamesFlow.firstOrNull() ?: emptySet()

        allCocktails = initialCocktailsFromSource.map { cocktail ->
            cocktail.copy(isFavorite = initialFavoriteNames.contains(cocktail.name))
        }
    }

    fun toggleFavorite(cocktailToToggle: Cocktail) {
        val newFavoriteState = !cocktailToToggle.isFavorite
        allCocktails = allCocktails.map {
            if (it.name == cocktailToToggle.name) {
                it.copy(isFavorite = newFavoriteState)
            } else {
                it
            }
        }
        scope.launch {
            if (newFavoriteState) {
                userPreferencesRepository.addFavorite(cocktailToToggle.name)
            } else {
                userPreferencesRepository.removeFavorite(cocktailToToggle.name)
            }
        }
    }

    if (allCocktails.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            Text("Ładowanie koktajli...", modifier = Modifier.padding(top = 60.dp))
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.name) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    onNavigateToFavorites = {
                        navController.navigate(Screen.Favorites.name) {
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.name
            ) {
                composable(Screen.Home.name) {
                    HomeScreen(
                        cocktails = allCocktails,
                        onCocktailClick = { cocktail ->
                            navController.navigate("cocktail_detail/${cocktail.name}")
                        },
                        openDrawer = { scope.launch { drawerState.open() } },
                        onFavoriteClick = ::toggleFavorite
                    )
                }
                composable(Screen.Favorites.name) {
                    FavoritesScreen(
                        cocktails = allCocktails,
                        onCocktailClick = { cocktail ->
                            navController.navigate("cocktail_detail/${cocktail.name}")
                        },
                        openDrawer = { scope.launch { drawerState.open() } },
                        onFavoriteClick = ::toggleFavorite
                    )
                }
                composable(
                    "cocktail_detail/{cocktailName}",
                    arguments = listOf(navArgument("cocktailName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val cocktailName = backStackEntry.arguments?.getString("cocktailName")
                    val cocktail = allCocktails.find { it.name == cocktailName }
                    cocktail?.let {
                        CocktailDetailScreen(
                            cocktail = it,
                            onBackPressed = { navController.popBackStack() },
                            onSendSms = onSendSms
                        )
                    }
                }
            }
        }
    }
}@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    openDrawer: () -> Unit,
    onFavoriteClick: (Cocktail) -> Unit
) {
    val tabItems = listOf("Alkoholowe", "Bezalkoholowe")
    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        AppHeader(
            title = "Koktajle",
            onMenuClick = openDrawer,
            showLogo = false,
        )
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> CocktailsGridScreen(
                    cocktails = cocktails.filter { it.isAlcoholic },
                    onCocktailClick = onCocktailClick,
                    onFavoriteClick = onFavoriteClick
                )
                1 -> CocktailsGridScreen(
                    cocktails = cocktails.filter { !it.isAlcoholic },
                    onCocktailClick = onCocktailClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    openDrawer: () -> Unit,
    onFavoriteClick: (Cocktail) -> Unit
) {
    val tabItems = listOf("Alkoholowe", "Bezalkoholowe")
    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val favoriteCocktails = remember(cocktails) {
        cocktails.filter { it.isFavorite }
    }

    Column(Modifier.fillMaxSize()) {
        AppHeader(
            title = "Ulubione",
            onMenuClick = openDrawer,
            showLogo = false
        )

        if (favoriteCocktails.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nie wybrałeś jeszcze ulubionych koktajli.",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        } else {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val cocktailsForTab = when (page) {
                    0 -> favoriteCocktails.filter { it.isAlcoholic }
                    1 -> favoriteCocktails.filter { !it.isAlcoholic }
                    else -> emptyList()
                }

                if (cocktailsForTab.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (page == 0) "Brak ulubionych koktajli alkoholowych." else "Brak ulubionych koktajli bezalkoholowych.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    CocktailsGridScreen(
                        cocktails = cocktailsForTab,
                        onCocktailClick = onCocktailClick,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
        }
    }
}

