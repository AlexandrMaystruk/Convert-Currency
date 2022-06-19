package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency

import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiEffect
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiEvent
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiState


sealed class ConvertCurrencyComposeEvent : UiEvent {

    data class ConvertCurrency(
        val selectedCurrencyCode: String,
        val amount: Double
    ) : ConvertCurrencyComposeEvent()

    override fun toString(): String = this.javaClass.simpleName

}

sealed class ConvertCurrencyViewState : UiState {

    object Loading : ConvertCurrencyViewState()

    data class Success(
        val fromCurrency: ConvertCurrencyView,
        val toCurrency: ConvertCurrencyView,
    ) : ConvertCurrencyViewState()

    override fun toString(): String = this.javaClass.simpleName
}


sealed class ConvertCurrencyEffect : UiEffect {
    class ShowToast(val stringRes: Int) : ConvertCurrencyEffect()
}


