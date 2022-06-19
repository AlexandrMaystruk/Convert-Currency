package com.gmail.maystruks08.currencyconverter.data.memory.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyTable(
    @PrimaryKey
    val code: String,
    val rate: Double
)
