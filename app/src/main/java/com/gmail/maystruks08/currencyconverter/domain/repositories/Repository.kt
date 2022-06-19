package com.gmail.maystruks08.currencyconverter.domain.repositories

import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAllCurrencies(filter: String? = null): Flow<List<CurrencyItem>>

    suspend fun getCurrencyByCode(currencyCode: String): CurrencyItem?

    suspend fun saveCurrencyToFavoriteList(currencyCode: String)

    suspend fun removeCurrencyToFavoriteList(currencyCode: String)

    suspend fun convertCurrency(from: CurrencyItem, to: CurrencyItem, amount: Double): Double

}