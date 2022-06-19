package com.gmail.maystruks08.currencyconverter.data.repositories

import com.gmail.maystruks08.currencyconverter.data.memory.tables.CurrencyTable
import com.gmail.maystruks08.currencyconverter.data.network.responces.GetAllCurrenciesResponse
import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import java.util.*

fun CurrencyTable.toEntity(isFavorite: Boolean): CurrencyItem {
    val currencySymbol = runCatching { Currency.getInstance(code) }.getOrNull()?.symbol.orEmpty()
    return CurrencyItem(
        symbol = currencySymbol,
        code = code,
        rate = rate,
        isFavorite = isFavorite
    )
}

fun GetAllCurrenciesResponse.toDatabaseEntities(): List<CurrencyTable> {
    return rates.map { CurrencyTable(code = it.key, rate = it.value) }
}