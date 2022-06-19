package com.gmail.maystruks08.currencyconverter.data.network.responces

import kotlinx.serialization.Serializable


@Serializable
data class CurrencyConvertingResponse(
    var motd: Motd? = Motd(),
    var success: Boolean? = null,
    var query: Query? = Query(),
    var info: Info? = Info(),
    var historical: Boolean? = null,
    var date: String? = null,
    var result: Double? = null
)

@Serializable
data class Info(var rate: Double? = null)

@Serializable
data class Motd(var msg: String? = null, var url: String? = null)

@Serializable
data class Query(
    var from: String? = null,
    var to: String? = null,
    var amount: Int? = null
)