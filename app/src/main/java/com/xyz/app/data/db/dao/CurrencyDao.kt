package com.xyz.app.data.db.dao

import androidx.room.*
import com.xyz.app.data.db.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency WHERE id = :currencyId ")
    suspend fun read(currencyId : String): CurrencyEntity

    @Query("SELECT * FROM currency WHERE id = :currencyId ")
    fun readAsFlow(currencyId : String): Flow<CurrencyEntity>

    @Delete
    suspend fun delete(currencyId: CurrencyEntity)

    @Query("DELETE FROM currency")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cameras: List<CurrencyEntity>)

    @Query("SELECT * FROM currency")
    fun readAllAsFlow(): Flow<List<CurrencyEntity>>

    @get:Query("SELECT * FROM currency")
    val all: List<CurrencyEntity>
}
