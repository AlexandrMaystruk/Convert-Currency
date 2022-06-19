
package com.gmail.maystruks08.currencyconverter.presentation.ui.home.models

sealed class ActionButton{
    object Add: ActionButton()
    object Remove: ActionButton()
}