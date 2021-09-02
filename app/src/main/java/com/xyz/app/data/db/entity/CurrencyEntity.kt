package com.xyz.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xyz.app.domain.CurrencyInfo

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String
) {
    fun asCurrencyInfo(): CurrencyInfo {
        return CurrencyInfo(id, name, symbol)
    }
}

fun CurrencyInfo.asEntity(): CurrencyEntity {
    return CurrencyEntity(id, name, symbol)
}
