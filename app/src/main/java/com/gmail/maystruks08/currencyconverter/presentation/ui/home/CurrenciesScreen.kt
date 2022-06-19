package com.gmail.maystruks08.currencyconverter.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.gmail.maystruks08.currencyconverter.presentation.navigation.AppNavigation
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ScreenMode
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.views.CurrencyItemsView
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.views.ShimmerItem
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.views.TopBarView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.animations.ShimmerAnimation
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.animations.simmerGreyEffectColors
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews.RetryView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
@ExperimentalMaterialApi
fun CurrenciesScreen(
    toolbarTitle: String,
    screenMode: ScreenMode,
    viewModel: CurrenciesScreenViewModel,
    navController: NavHostController
) {
    val viewState = viewModel.uiState.collectAsState()

    val searchQuery = remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopBarView(
                title = toolbarTitle,
                onSearchTextChanged = { filterQuery ->
                    searchQuery.value = filterQuery
                },
                onSearchClosed = {
                    searchQuery.value = null
                    viewModel.setEvent(CurrenciesScreenEvent.Reload(screenMode))
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        content = {
            when (val state = viewState.value) {
                CurrenciesViewState.Loading -> {
                    Column {
                        repeat(12) {
                            ShimmerAnimation(simmerGreyEffectColors) {
                                ShimmerItem(brush = it)
                            }
                        }
                    }
                }
                is CurrenciesViewState.Error -> {
                    when (state) {
                        CurrenciesViewState.Error.GeneralError -> {
                            RetryView(
                                title = "Retry",
                                icon = Icons.Rounded.Error,
                                retryButtonClicked = {
                                    viewModel.setEvent(CurrenciesScreenEvent.Reload(screenMode))
                                })
                        }

                        else -> {

                        }
                    }
                }
                is CurrenciesViewState.Success -> CurrencyItemsView(
                    currencyItems = state.currencyItems,
                    isSwipeEnabled = state.isSwipeEnabled,
                    filterQuery = searchQuery,
                    onAddButtonClicked = {
                        viewModel.setEvent(
                            CurrenciesScreenEvent.AddCurrencyToFavoriteClicked(screenMode, it)
                        )
                    },
                    onRemoveButtonClicked = {
                        viewModel.setEvent(
                            CurrenciesScreenEvent.RemoveCurrencyFromFavoriteClicked(
                                screenMode, it
                            )
                        )
                    },
                    navigateToConvertCurrency = {
                        navController.navigate(AppNavigation.ConvertCurrency.withArgs(it.code))
                    }
                )
            }
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        launch {
            viewModel.effect.collect {
                when (it) {
                    is CurrenciesEffect.ShowToast -> {

                    }
                }
            }
        }

        viewModel.setEvent(CurrenciesScreenEvent.Reload(screenMode))
    })
}



