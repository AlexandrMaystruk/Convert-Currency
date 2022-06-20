package com.gmail.maystruks08.currencyconverter.presentation.ui.home

import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ScreenMode
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiEffect
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiEvent
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiState


sealed class CurrenciesScreenEvent : UiEvent {

    data class Reload(val screenMode: ScreenMode) : CurrenciesScreenEvent()

    data class AddCurrencyToFavoriteClicked(
        val screenMode: ScreenMode,
        val currencyItemView: CurrencyItemView
    ) : CurrenciesScreenEvent()

    data class RemoveCurrencyFromFavoriteClicked(
        val screenMode: ScreenMode,
        val currencyItemView: CurrencyItemView
    ) : CurrenciesScreenEvent()

}


sealed class CurrenciesViewState : UiState {

    object Loading : CurrenciesViewState()

    data class Success(
        val currencyItems: List<CurrencyItemView>,
        val isSwipeEnabled: Boolean
    ) : CurrenciesViewState()

    sealed class Error : CurrenciesViewState() {
        object NoInternet : Error()
        object NoCachedData : Error()
        object GeneralError : Error()
    }

    override fun toString(): String = this.javaClass.simpleName

}


sealed class CurrenciesEffect : UiEffect {
    class ShowToast(val stringRes: Int) : CurrenciesEffect()
}


