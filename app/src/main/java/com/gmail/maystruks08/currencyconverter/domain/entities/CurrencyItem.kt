package com.gmail.maystruks08.currencyconverter.domain.entities


data class CurrencyItem(
    val symbol: String,
    val code: String,
    val rate: Double,
    val isFavorite: Boolean
)