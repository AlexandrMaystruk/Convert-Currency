package com.gmail.maystruks08.currencyconverter.data.memory

import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ActionButton
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView
import kotlin.random.Random

object DefaultData {

    val currencyViews = mutableListOf<CurrencyItemView>().apply {
        repeat(120) {
            add(
                CurrencyItemView(
                    code = "Currency $it",
                    actionButtonState = if (Random.nextBoolean()) ActionButton.Add else ActionButton.Remove,
                )
            )
        }
    }
}