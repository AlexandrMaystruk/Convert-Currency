package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency

import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import com.gmail.maystruks08.currencyconverter.domain.entities.ConvertResult
import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import com.gmail.maystruks08.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ConvertCurrencyScreenViewModelTest {

    @MockK
    lateinit var appDispatchers: AppDispatchers

    @MockK
    lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldCorrectHandleEventWithZeroAmount() = runTest {
        val currencyCodeFrom = "EUR"
        val currencyCodeTo = "USD"

        val itemFrom = CurrencyItem("$", currencyCodeTo, 1.0, false)
        val itemTo = CurrencyItem("EUR", currencyCodeTo, 1.5, false)

        val itemViewFrom = ConvertCurrencyView("$", currencyCodeTo, "0.0")
        val itemViewTo = ConvertCurrencyView("EUR", currencyCodeTo, "0.0")

        val currencyViewModel = ConvertCurrencyScreenViewModel(
            appDispatchers = appDispatchers,
            convertCurrencyUseCase = convertCurrencyUseCase
        )

        coEvery { convertCurrencyUseCase.invoke(currencyCodeFrom, 0.0) } returns ConvertResult(
            itemFrom,
            0.0,
            itemTo,
            0.0
        )

        assertTrue(currencyViewModel.uiState.value is ConvertCurrencyViewState.Loading)

        currencyViewModel.setEvent(ConvertCurrencyComposeEvent.Reload(currencyCodeTo, 0.0))

        assertTrue(currencyViewModel.uiState.value is ConvertCurrencyViewState.Success)
        assertTrue((currencyViewModel.uiState.value as ConvertCurrencyViewState.Success).toCurrency == itemViewTo)
        assertTrue((currencyViewModel.uiState.value as ConvertCurrencyViewState.Success).fromCurrency == itemViewFrom)

        coVerify { convertCurrencyUseCase.invoke(currencyCodeFrom, 0.0) }
    }

}