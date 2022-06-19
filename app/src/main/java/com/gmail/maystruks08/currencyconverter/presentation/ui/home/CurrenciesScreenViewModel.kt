package com.gmail.maystruks08.currencyconverter.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.gmail.maystruks08.currencyconverter.domain.usecases.AddCurrencyToFavorite
import com.gmail.maystruks08.currencyconverter.domain.usecases.FetchCurrenciesUseCase
import com.gmail.maystruks08.currencyconverter.domain.usecases.RemoveCurrencyFromFavorite
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ActionButton
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ScreenMode
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.base.BaseViewModel
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrenciesScreenViewModel @Inject constructor(
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase,
    private val addCurrencyToFavorite: AddCurrencyToFavorite,
    private val removeCurrencyFromFavorite: RemoveCurrencyFromFavorite,
) : BaseViewModel<CurrenciesScreenEvent, CurrenciesViewState, CurrenciesEffect>() {

    private var reloadJob: Job? = null

    override fun createInitialState() = CurrenciesViewState.Loading

    override fun handleEvent(event: CurrenciesScreenEvent) {
        when (val state = currentState) {
            is CurrenciesViewState.Error, is CurrenciesViewState.Loading -> commonReduceForErrorAndLoading(
                event
            )
            is CurrenciesViewState.Success -> reduce(event, state)
        }
    }

    private fun commonReduceForErrorAndLoading(event: CurrenciesScreenEvent) {
        Timber.d("Current state: ${currentState}, event: $event")
        when (event) {
            is CurrenciesScreenEvent.Reload -> {
                setState { CurrenciesViewState.Loading }
                reloadContent(event.screenMode)
            }
            else -> throw RuntimeException("Incorrect app state")
        }
    }

    private fun reduce(
        event: CurrenciesScreenEvent,
        currentState: CurrenciesViewState.Success
    ) {
        Timber.d("Current state: $currentState, event: $event")
        when (event) {
            is CurrenciesScreenEvent.Reload -> reloadContent(event.screenMode)
            is CurrenciesScreenEvent.AddCurrencyToFavoriteClicked -> {
                addCurrencyToFavorite(
                    currentState.currencyItems,
                    event.screenMode,
                    event.currencyItemView
                )
            }
            is CurrenciesScreenEvent.RemoveCurrencyFromFavoriteClicked -> {
                removeCurrencyFromFavorite(
                    currentState.currencyItems,
                    event.screenMode,
                    event.currencyItemView
                )
            }
        }
    }

    private fun reloadContent(screenMode: ScreenMode) {
        reloadJob?.cancel()
        reloadJob = viewModelScope.launch {
            val onlyFavorite = when (screenMode) {
                ScreenMode.AllCurrency -> false
                ScreenMode.MyCurrency -> true
            }
            fetchCurrenciesUseCase
                .invoke(onlyFavorite)
                .cancellable()
                .catch { exception ->
                    setState { CurrenciesViewState.Error.GeneralError }
                    Timber.d(exception.localizedMessage.orEmpty())
                }
                .map { items ->
                    val isButtonVisible = screenMode is ScreenMode.AllCurrency
                    items.map { it.toUi(isButtonVisible) }
                }
                .collect { currencyItems ->
                    setState {
                        CurrenciesViewState.Success(currencyItems, isSwipeItemEnabled(screenMode))
                    }
                }
        }
    }

    private fun addCurrencyToFavorite(
        currencyItems: List<CurrencyItemView>,
        screenMode: ScreenMode,
        currencyItemView: CurrencyItemView
    ) {
        viewModelScope.launch {
            runCatching {
                addCurrencyToFavorite.invoke(currencyItemView.code)
                currencyItems.map {
                    if (it.code != currencyItemView.code) return@map it
                    return@map it.copy(actionButtonState = ActionButton.Remove)
                }
            }.onSuccess {
                setState { CurrenciesViewState.Success(it, isSwipeItemEnabled(screenMode)) }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun removeCurrencyFromFavorite(
        currencyItems: List<CurrencyItemView>,
        screenMode: ScreenMode,
        currencyItemView: CurrencyItemView
    ) {
        viewModelScope.launch {
            runCatching {
                removeCurrencyFromFavorite.invoke(currencyItemView.code)
                when (screenMode) {
                    ScreenMode.AllCurrency -> {
                        currencyItems.map {
                            if (it.code != currencyItemView.code) return@map it
                            return@map it.copy(actionButtonState = ActionButton.Add)
                        }
                    }
                    ScreenMode.MyCurrency -> {
                        currencyItems.mapNotNull {
                            if (it.code == currencyItemView.code) return@mapNotNull null
                            it.copy(actionButtonState = null)
                        }
                    }
                }
            }.onSuccess {
                setState { CurrenciesViewState.Success(it, isSwipeItemEnabled(screenMode)) }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun isSwipeItemEnabled(screenMode: ScreenMode) = screenMode is ScreenMode.MyCurrency

}


