package com.gmail.maystruks08.currencyconverter.data.repositories

import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppCoroutineDispatchersImpl @Inject constructor() : AppDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
}