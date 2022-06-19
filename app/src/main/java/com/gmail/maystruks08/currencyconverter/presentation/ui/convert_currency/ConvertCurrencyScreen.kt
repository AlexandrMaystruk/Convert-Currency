package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency

import android.widget.Toast
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.views.ContentView
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.views.ConvertCurrencyRootView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ConvertCurrencyScreen(
    selectedCurrencyCode: String,
    viewModel: ConvertCurrencyScreenViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    val viewState = viewModel.uiState.collectAsState()

    ConvertCurrencyRootView(
        convertScreenTitle = "Convert currency",
        onBackButtonClicked = { navController.popBackStack() },
        content = {
            when (val state = viewState.value) {
                ConvertCurrencyViewState.Loading -> {
                    CircularProgressIndicator()
                }
                is ConvertCurrencyViewState.Success -> {
                    ContentView(
                        fromCurrencyItemView = state.fromCurrency,
                        toCurrencyItemView = state.toCurrency,
                        onConvertCurrencyClicked = {
                            viewModel.setEvent(
                                ConvertCurrencyComposeEvent.Reload(selectedCurrencyCode, it)
                            )
                        }
                    )
                }
            }
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        launch {
            viewModel.effect.collect {
                when (it) {
                    is ConvertCurrencyEffect.ShowToast -> Toast.makeText(
                        context,
                        context.getString(it.stringRes),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.setEvent(ConvertCurrencyComposeEvent.Reload(selectedCurrencyCode, 0.0))
    })
}