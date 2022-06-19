package com.gmail.maystruks08.currencyconverter.presentation.ui.home.models

sealed class ScreenMode {
    object AllCurrency : ScreenMode()
    object MyCurrency : ScreenMode()
}
