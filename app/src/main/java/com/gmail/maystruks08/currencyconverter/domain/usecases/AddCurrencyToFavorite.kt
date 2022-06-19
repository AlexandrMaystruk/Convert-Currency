package com.gmail.maystruks08.currencyconverter.domain.usecases

import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import javax.inject.Inject

interface AddCurrencyToFavorite {

    suspend fun invoke(currencyCode: String)

}

class AddCurrencyToFavoriteImpl @Inject constructor(
    private val repository: Repository
) : AddCurrencyToFavorite {

    override suspend fun invoke(currencyCode: String) {
        repository.saveCurrencyToFavoriteList(currencyCode)
    }

}