package com.gmail.maystruks08.currencyconverter.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.maystruks08.currencyconverter.R

sealed class AppNavigation(val route: String) {

    object ConvertCurrency : AppNavigation("ConvertCurrency")

    sealed class BottomNavigationScreens(
        route: String,
        @StringRes val resourceId: Int,
        val icon: ImageVector
    ) : AppNavigation(route) {

        object AllCurrency : BottomNavigationScreens(
            "AllCurrency",
            R.string.all_currencies,
            Icons.Filled.MonetizationOn
        )

        object MyCurrency : BottomNavigationScreens(
            "MyCurrency",
            R.string.my_currencies,
            Icons.Filled.Star
        )
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}