package com.gmail.maystruks08.currencyconverter.data.datastores

import com.gmail.maystruks08.currencyconverter.data.memory.dao.CurrencyDao
import com.gmail.maystruks08.currencyconverter.data.memory.tables.CurrencyTable
import com.gmail.maystruks08.currencyconverter.data.memory.dao.FavoritesDao
import com.gmail.maystruks08.currencyconverter.data.memory.tables.FavoritesTable
import javax.inject.Inject

class CurrencyLocaleDataSource @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val favoritesDao: FavoritesDao
) {

    suspend fun saveCurrencies(items: List<CurrencyTable>) {
        currencyDao.insertALl(items)
    }

    suspend fun fetchCurrencies(): List<CurrencyTable> {
        return currencyDao.getAll()
    }

    suspend fun getCurrencyByCode(currencyCode: String): CurrencyTable {
        return currencyDao.getByCode(currencyCode)
    }

    suspend fun fetchFavoriteList(): List<FavoritesTable> {
        return favoritesDao.getAll()
    }

    suspend fun addCurrencyToFavoriteList(currencyCode: String) {
        favoritesDao.insert(FavoritesTable(code = currencyCode))
    }

    suspend fun removeCurrencyToFavoriteList(currencyCode: String) {
        favoritesDao.delete(currencyCode)
    }

}