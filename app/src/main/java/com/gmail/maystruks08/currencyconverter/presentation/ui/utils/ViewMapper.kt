package com.gmail.maystruks08.currencyconverter.presentation.ui.utils

import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ActionButton
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView

fun CurrencyItem.toUi(isButtonVisible: Boolean) = CurrencyItemView(
    code = code,
    actionButtonState = if (isButtonVisible) {
        if (isFavorite) ActionButton.Remove else ActionButton.Add
    } else null
)