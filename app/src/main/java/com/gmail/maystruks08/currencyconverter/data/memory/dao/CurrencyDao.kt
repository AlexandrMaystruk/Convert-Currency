package com.gmail.maystruks08.currencyconverter.data.memory.dao

import androidx.room.*
import com.gmail.maystruks08.currencyconverter.data.memory.tables.CurrencyTable

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertALl(currencyTables: List<CurrencyTable>)

    @Query("SELECT * FROM CurrencyTable")
    suspend fun getAll(): List<CurrencyTable>

    @Query("SELECT * FROM CurrencyTable WHERE code =:currencyCode")
    suspend fun getByCode(currencyCode: String): CurrencyTable

    @Delete
    suspend fun delete(currencyTables: CurrencyTable)

}