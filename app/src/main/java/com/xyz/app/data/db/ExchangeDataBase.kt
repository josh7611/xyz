package com.xyz.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xyz.app.data.db.dao.CurrencyDao
import com.xyz.app.data.db.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class ExchangeDataBase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}

