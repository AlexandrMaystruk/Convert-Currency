package com.gmail.maystruks08.currencyconverter.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gmail.maystruks08.currencyconverter.presentation.navigation.AppNavigation
import com.gmail.maystruks08.currencyconverter.presentation.theme.ConvertCurrencyTheme
import com.gmail.maystruks08.currencyconverter.presentation.theme.Spacing
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.ConvertCurrencyScreen
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.CurrenciesScreen
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ScreenMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalFoundationApi
@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    var appState: AppState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConvertCurrencyTheme {
                val appState = rememberAppState().also { appState = it }
                Scaffold(
                    bottomBar = { AppBottomNavigation(appState = appState) }
                ) {
                    MainScreenNavigationConfigurations(appState)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appState = null
    }
}

@Composable
private fun AppBottomNavigation(appState: AppState) {
    if (!appState.isBottomBarVisible) return

    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(Spacing.small)
            .clickable(enabled = false) { }
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        appState.bottomBarItems.forEach {
            CustomBottomNavigationItem(
                item = it,
                isSelected = it == appState.currentSelectedBottomNavigationScreen.value,
                onClick = {
                    if (it != appState.currentSelectedBottomNavigationScreen.value) {
                        val previousRoute =
                            appState.currentSelectedBottomNavigationScreen.value.route
                        appState.currentSelectedBottomNavigationScreen.value = it
                        appState.navController.navigate(it.route) {
                            popUpTo(previousRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun CustomBottomNavigationItem(
    item: AppNavigation.BottomNavigationScreens,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background =
        if (isSelected) MaterialTheme.colors.primary.copy(0.1f)
        else Color.Transparent

    val contentColor =
        if (isSelected) MaterialTheme.colors.primary
        else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(Spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.extraSmall)
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = stringResource(id = item.resourceId),
                    color = contentColor
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
private fun MainScreenNavigationConfigurations(appState: AppState) {
    NavHost(
        navController = appState.navController,
        startDestination = AppNavigation.BottomNavigationScreens.AllCurrency.route
    ) {
        composable(AppNavigation.BottomNavigationScreens.AllCurrency.route) {
            CurrenciesScreen(
                screenMode = ScreenMode.AllCurrency,
                viewModel = hiltViewModel(),
                navController = appState.navController
            )
        }
        composable(AppNavigation.BottomNavigationScreens.MyCurrency.route) {
            CurrenciesScreen(
                screenMode = ScreenMode.MyCurrency,
                viewModel = hiltViewModel(),
                navController = appState.navController
            )
        }

        composable(
            route = AppNavigation.ConvertCurrency.route + "/{selectedCurrencyCode}",
            arguments = listOf(
                navArgument("selectedCurrencyCode") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
        ) {
            val selectedCurrencyCode = it.arguments?.getString("selectedCurrencyCode")
            checkNotNull(selectedCurrencyCode)
            ConvertCurrencyScreen(selectedCurrencyCode, hiltViewModel(), appState.navController)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@InternalCoroutinesApi
fun Context.getAppState(): AppState? {
    return (this as? MainActivity)?.appState
}