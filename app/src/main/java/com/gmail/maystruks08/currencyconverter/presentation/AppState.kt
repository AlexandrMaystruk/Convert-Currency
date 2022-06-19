package com.gmail.maystruks08.currencyconverter.presentation

import android.content.res.Resources
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gmail.maystruks08.currencyconverter.presentation.navigation.AppNavigation
import kotlinx.coroutines.CoroutineScope

class AppState(
    val navController: NavHostController,
    val resources: Resources,
    val coroutineScope: CoroutineScope
) {

    private val items = listOf(
        AppNavigation.BottomNavigationScreens.AllCurrency,
        AppNavigation.BottomNavigationScreens.MyCurrency
    )

    val bottomBarItems: List<AppNavigation.BottomNavigationScreens>
        @Composable get() = items

    private val bottomBarRoutes
        @Composable get() = bottomBarItems.map { it.route }

    val isBottomBarVisible: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentSelectedBottomNavigationScreen =
        mutableStateOf<AppNavigation.BottomNavigationScreens>(
            AppNavigation.BottomNavigationScreens.AllCurrency
        )
}

/**
 * A composable function that returns the [Resources]. It will be recomposed when `Configuration`
 * gets updated.
 */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController, resources, coroutineScope) {
    AppState(navController, resources, coroutineScope)
}

fun NavHostController.getCurrentRoute(): String? = currentBackStackEntry?.destination?.route
