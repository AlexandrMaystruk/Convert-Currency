package com.gmail.maystruks08.currencyconverter.presentation.ui

import com.gmail.maystruks08.currencyconverter.domain.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class TestCoroutinesDispatchers(
    override val main: CoroutineDispatcher = StandardTestDispatcher(),
    override val io: CoroutineDispatcher = StandardTestDispatcher(),
    override val default: CoroutineDispatcher = StandardTestDispatcher(),
): AppDispatchers