package com.gmail.maystruks08.currencyconverter.domain.usecases

import com.gmail.maystruks08.currencyconverter.domain.entities.ConvertResult
import com.gmail.maystruks08.currencyconverter.domain.repositories.Repository
import javax.inject.Inject

interface ConvertCurrencyUseCase {

    suspend fun invoke(currencyCode: String, amount: Double): ConvertResult

}

class ConvertCurrencyUseCaseImpl @Inject constructor(
    private val repository: Repository
) : ConvertCurrencyUseCase {

    override suspend fun invoke(currencyCode: String, amount: Double): ConvertResult {
        val from = repository.getCurrencyByCode(currencyCode) ?: throw RuntimeException()
        val to = repository.getCurrencyByCode("USD") ?: throw RuntimeException()
        if (amount == 0.0) {
            return ConvertResult(from, 0.0, to, 0.0)
        }
        val totalAmount = repository.convertCurrency(from, to, amount)
        return ConvertResult(from, amount, to, totalAmount)
    }

}
