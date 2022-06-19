package com.gmail.maystruks08.currencyconverter.data.network

import com.gmail.maystruks08.currencyconverter.data.network.responces.CurrencyConvertingResponse
import com.gmail.maystruks08.currencyconverter.data.network.responces.GetAllCurrenciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET(HttpRoutes.CURRENCY_LIST)
    suspend fun getCurrencyList(): Response<GetAllCurrenciesResponse>

    @GET(HttpRoutes.CONVERT)
    suspend fun convertCurrency(
        @Query("from") currencyCodeFrom: String,
        @Query("to") currencyCodeTo: String,
        @Query("amount") amount: String
    ): Response<CurrencyConvertingResponse>

}
