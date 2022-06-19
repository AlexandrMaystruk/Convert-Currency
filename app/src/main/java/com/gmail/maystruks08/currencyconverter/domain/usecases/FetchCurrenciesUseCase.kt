package com.gmail.maystruks08.currencyconverter.domain.usecases

import com.gmail.maystruks08.currencyconverter.domain.entities.CurrencyItem
import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FetchCurrenciesUseCase {

    suspend fun invoke(onlyFavorite: Boolean, filter: String? = null): Flow<List<CurrencyItem>>

}

class FetchAllCurrenciesUseCaseImpl @Inject constructor(
    private val repository: Repository
) : FetchCurrenciesUseCase {

    override suspend fun invoke(onlyFavorite: Boolean, filter: String?): Flow<List<CurrencyItem>> {
        return repository
            .getAllCurrencies()
            .map { list ->
                if (!onlyFavorite) return@map list
                return@map list.filter { it.isFavorite }
            }
    }

}
