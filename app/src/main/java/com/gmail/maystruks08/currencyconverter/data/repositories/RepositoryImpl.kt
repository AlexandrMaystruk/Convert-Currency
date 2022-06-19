package com.gmail.maystruks08.currencyconverter.data.repositories

import com.gmail.maystruks08.currencyconverter.data.datastores.CurrencyLocaleDataSource
import com.gmail.maystruks08.currencyconverter.data.datastores.CurrencyRemoteDataSource
import com.gmail.maystruks08.currencyconverter.data.network.NetworkUtil
import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import com.gmail.maystruks08.currencyconverter.domain.exceptions.AppError
import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyLocaleDataSource: CurrencyLocaleDataSource,
    private val appDispatchers: AppDispatchers,
    private val networkUtil: NetworkUtil
) : Repository {

    private val currencyListMutex = Mutex()
    private var currencyList: List<CurrencyItem> = emptyList()

    private val favoritesMutex = Mutex()
    private var favorites: HashMap<String, Boolean> = hashMapOf()

    override suspend fun getAllCurrencies(filter: String?): Flow<List<CurrencyItem>> {
        return flow {
            if (favorites.isEmpty()) fetchFavorites()
            val cachedData = currencyLocaleDataSource.fetchCurrencies()
            if (cachedData.isNotEmpty()) {
                val items = cachedData.map {
                    it.toEntity(favorites[it.code] ?: false)
                }
                emit(items)
                currencyListMutex.withLock {
                    currencyList = items
                }
            }

            if (!networkUtil.isNetworkTurnedOn()) return@flow

            val remoteResponse = currencyRemoteDataSource.fetchCurrencyList()
            val databaseEntityList = remoteResponse.toDatabaseEntities()
            val items = databaseEntityList.map {
                it.toEntity(favorites[it.code] ?: false)
            }
            emit(items)
            currencyLocaleDataSource.saveCurrencies(databaseEntityList)
            currencyListMutex.withLock { currencyList = items }
        }.flowOn(appDispatchers.io)
    }

    override suspend fun getCurrencyByCode(currencyCode: String): CurrencyItem? {
        return currencyList.firstOrNull { it.code == currencyCode }
    }

    override suspend fun saveCurrencyToFavoriteList(currencyCode: String) {
        withContext(appDispatchers.io) {
            currencyLocaleDataSource.addCurrencyToFavoriteList(currencyCode)
            favoritesMutex.withLock {
                favorites[currencyCode] = true
            }
        }
    }

    override suspend fun removeCurrencyToFavoriteList(currencyCode: String) {
        withContext(appDispatchers.io) {
            currencyLocaleDataSource.removeCurrencyToFavoriteList(currencyCode)
            favoritesMutex.withLock {
                favorites.remove(currencyCode)
            }
        }
    }

    override suspend fun convertCurrency(
        from: CurrencyItem,
        to: CurrencyItem,
        amount: Double
    ): Double {
        if (!networkUtil.isNetworkTurnedOn()) throw AppError.LocaleExceptions.NoConnection

        return withContext(appDispatchers.io) {
            currencyRemoteDataSource.getCurrencyRateInfo(
                from.code,
                to.code,
                amount
            ).result ?: throw AppError.RemoteExceptions.ResponseEmptyError
        }
    }

    private suspend fun fetchFavorites() {
        withContext(appDispatchers.io) {
            favoritesMutex.withLock {
                currencyLocaleDataSource
                    .fetchFavoriteList()
                    .forEach { favorites[it.code] = true }
            }
        }
    }

}
