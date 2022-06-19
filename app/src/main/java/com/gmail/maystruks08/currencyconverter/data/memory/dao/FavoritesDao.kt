package com.gmail.maystruks08.currencyconverter.data.memory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.maystruks08.currencyconverter.data.memory.tables.FavoritesTable

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tableItem: FavoritesTable)

    @Query("SELECT * FROM FavoritesTable")
    suspend fun getAll(): List<FavoritesTable>

    @Query("SELECT * FROM FavoritesTable WHERE code =:currencyCode")
    suspend fun getByCode(currencyCode: String): FavoritesTable

    @Query("DELETE FROM FavoritesTable WHERE code =:currencyCode")
    suspend fun delete(currencyCode: String)

}