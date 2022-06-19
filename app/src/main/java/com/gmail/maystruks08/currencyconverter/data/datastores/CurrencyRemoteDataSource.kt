package com.gmail.maystruks08.currencyconverter.data.datastores

import com.gmail.maystruks08.currencyconverter.data.network.CurrencyApiService
import com.gmail.maystruks08.currencyconverter.data.network.responces.CurrencyConvertingResponse
import com.gmail.maystruks08.currencyconverter.data.network.responces.GetAllCurrenciesResponse
import com.gmail.maystruks08.currencyconverter.domain.exceptions.AppError
import retrofit2.Response
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val api: CurrencyApiService
) {

    suspend fun fetchCurrencyList(): GetAllCurrenciesResponse {
        val response = api.getCurrencyList()
        return handleResponse(response)
    }

    suspend fun getCurrencyRateInfo(
        currencyCodeFrom: String,
        currencyCodeTo: String,
        amount: Double
    ): CurrencyConvertingResponse {
        val response = api.convertCurrency(currencyCodeFrom, currencyCodeTo, amount.toString())
        return handleResponse(response)
    }

    private fun <T> handleResponse(response: Response<T>): T {
        if (response.isSuccessful) {
            val body = response.body() ?: kotlin.run {
                throw AppError.RemoteExceptions.ResponseEmptyError
            }
            return body
        } else {
            handleApiError(response)
        }
    }

    private fun <T> handleApiError(response: Response<T>): Nothing {
        if (!response.isSuccessful) {
            when (response.code()) {
                201, 202, 203 -> throw AppError.RemoteExceptions.AuthorizationError
                404 -> throw AppError.RemoteExceptions.DataNotFound
                else -> throw AppError.RemoteExceptions.InternalServerError
            }
        }
        throw Exception("Unhandled exception")
    }

}
