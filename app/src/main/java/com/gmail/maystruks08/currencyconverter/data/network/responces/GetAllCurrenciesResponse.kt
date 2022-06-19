package com.gmail.maystruks08.currencyconverter.data.network.responces

import kotlinx.serialization.Serializable


@Serializable
data class GetAllCurrenciesResponse(
    var motd: Motd? = Motd(),
    var success: Boolean? = null,
    var base: String? = null,
    var date: String? = null,
    var rates: Map<String, Double>
)