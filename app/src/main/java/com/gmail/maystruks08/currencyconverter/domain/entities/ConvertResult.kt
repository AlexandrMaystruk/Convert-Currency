package com.gmail.maystruks08.currencyconverter.domain.entities


data class ConvertResult(
    val fromCurrency: CurrencyItem,
    val fromCurrencyValue: Double,
    val toCurrency: CurrencyItem,
    val toCurrencyValue: Double
)
