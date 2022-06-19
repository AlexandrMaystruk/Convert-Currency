package com.gmail.maystruks08.currencyconverter.data.memory.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesTable(
    @PrimaryKey
    val code: String
)
