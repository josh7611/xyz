package com.xyz.app.infrastructure

import androidx.room.Room
import com.xyz.app.DemoApplication
import com.xyz.app.data.data_source.CurrencyInfoLocalDataSourceImpl
import com.xyz.app.data.db.ExchangeDataBase
import com.xyz.app.data.repository.CurrencyInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KProperty

object SimpleDi {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): CurrencyInfoRepository {
        return CurrencyInfoRepository(
            CurrencyInfoLocalDataSourceImpl(
                Dispatchers.IO,
                provideDataBase().currencyDao()
            )
        )
    }

    private fun provideDataBase(
    ): ExchangeDataBase {
        return Room.databaseBuilder(
            DemoApplication.getContext()!!,
            ExchangeDataBase::class.java, "currency-db"
        ).build()
    }
}