package com.gmail.maystruks08.currencyconverter.domain.usecases

import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import javax.inject.Inject

interface RemoveCurrencyFromFavorite {

    suspend fun invoke(currencyCode: String)

}

class RemoveCurrencyFromFavoriteImpl @Inject constructor(
    private val repository: Repository
) : RemoveCurrencyFromFavorite {

    override suspend fun invoke(currencyCode: String) {
        repository.removeCurrencyToFavoriteList(currencyCode)
    }

}
