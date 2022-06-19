package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.views

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews.BackButtonView

@Composable
fun ConvertCurrencyRootView(
    convertScreenTitle: String,
    content: @Composable () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = convertScreenTitle)
                },
                navigationIcon = {
                    BackButtonView(onBackButtonClicked)
                })
        },
        backgroundColor = MaterialTheme.colors.background,
        content = { content.invoke() }
    )
}


@Preview(showBackground = true)
@Composable
fun ConvertCurrencyRootView_Preview() {
    ConvertCurrencyRootView(
        convertScreenTitle = "Convert screen",
        content = {
            ContentView(
                ConvertCurrencyView("$", "USD", "100"),
                ConvertCurrencyView("#","EUR", "200"),
                {}
            )
        },
        onBackButtonClicked = {}
    )
}