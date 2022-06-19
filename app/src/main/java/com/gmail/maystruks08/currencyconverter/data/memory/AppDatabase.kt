package com.gmail.maystruks08.currencyconverter.data.memory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.maystruks08.currencyconverter.data.memory.dao.CurrencyDao
import com.gmail.maystruks08.currencyconverter.data.memory.dao.FavoritesDao
import com.gmail.maystruks08.currencyconverter.data.memory.tables.CurrencyTable
import com.gmail.maystruks08.currencyconverter.data.memory.tables.FavoritesTable

@Database(entities = [CurrencyTable::class, FavoritesTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun favoritesDao(): FavoritesDao
}