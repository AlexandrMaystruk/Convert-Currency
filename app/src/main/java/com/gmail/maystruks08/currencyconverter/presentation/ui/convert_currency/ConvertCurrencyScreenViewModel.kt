package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency

import androidx.lifecycle.viewModelScope
import com.gmail.maystruks08.currencyconverter.R
import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import com.gmail.maystruks08.currencyconverter.domain.exceptions.AppError
import com.gmail.maystruks08.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.BaseViewModel
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyScreenViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val appDispatchers: AppDispatchers
) : BaseViewModel<ConvertCurrencyComposeEvent, ConvertCurrencyViewState, ConvertCurrencyEffect>() {

    override fun createInitialState() = ConvertCurrencyViewState.Loading

    override fun handleEvent(event: ConvertCurrencyComposeEvent) {
        when (val state = currentState) {
            is ConvertCurrencyViewState.Loading -> reduce(event, state)
            is ConvertCurrencyViewState.Success -> reduce(event, state)
        }
    }

    private fun reduce(
        event: UiEvent,
        currentState: ConvertCurrencyViewState.Loading
    ) {
        Timber.d("Current state: $currentState, event: $event")
        when (event) {
            is ConvertCurrencyComposeEvent.ConvertCurrency -> reloadContent(
                event.selectedCurrencyCode,
                event.amount
            )
            else -> throw RuntimeException("Incorrect app state")
        }
    }

    private fun reduce(
        event: UiEvent,
        currentState: ConvertCurrencyViewState.Success
    ) {
        Timber.d("Current state: $currentState, event: $event")
        when (event) {
            is ConvertCurrencyComposeEvent.ConvertCurrency -> reloadContent(
                event.selectedCurrencyCode,
                event.amount
            )
            else -> throw RuntimeException("Incorrect app state")
        }
    }

    private fun reloadContent(selectedCurrencyCode: String, amount: Double) {
        viewModelScope.launch(appDispatchers.io) {
            runCatching {
                convertCurrencyUseCase.invoke(selectedCurrencyCode, amount)
            }.onSuccess {
                Timber.d("Convert result: $it")
                setState {
                    ConvertCurrencyViewState.Success(
                        ConvertCurrencyView(
                            it.fromCurrency.symbol,
                            it.fromCurrency.code,
                            it.fromCurrencyValue.toString(),
                        ),
                        ConvertCurrencyView(
                            it.toCurrency.symbol,
                            it.toCurrency.code,
                            it.toCurrencyValue.toString()
                        )
                    )
                }
            }.onFailure {
                Timber.e(it)
                val errorTextRes = when (it) {
                    is AppError.LocaleExceptions.NoCachedData -> R.string.error_no_cached_data
                    is AppError.LocaleExceptions.NoConnection -> R.string.error_no_connection

                    is AppError.RemoteExceptions -> R.string.error_server
                    else -> R.string.error_global
                }
                setEffect { ConvertCurrencyEffect.ShowToast(errorTextRes) }
            }
        }
    }
}
