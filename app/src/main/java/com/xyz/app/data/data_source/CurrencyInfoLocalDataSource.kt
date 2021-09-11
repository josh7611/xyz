package com.xyz.app.data.data_source

import com.xyz.app.data.db.dao.CurrencyDao
import com.xyz.app.data.db.entity.asEntity
import com.xyz.app.data.db.executeDaoCall
import com.xyz.app.domain.CurrencyInfo
import kotlinx.coroutines.CoroutineDispatcher

interface CurrencyInfoLocalDataSource {
    suspend fun read(currencyId: String): CurrencyInfo
    suspend fun update(currencyInfos: List<CurrencyInfo>)
    suspend fun delete(currencyInfo: CurrencyInfo)

    suspend fun list(): List<CurrencyInfo>

    suspend fun clear()
}

internal class CurrencyInfoLocalDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val currencyDao: CurrencyDao
) : CurrencyInfoLocalDataSource {

    override suspend fun read(currencyId: String): CurrencyInfo {
        return executeDaoCall(
            ioDispatcher = ioDispatcher,
            daoCall = {
                currencyDao.read(currencyId)
            }
        ).asCurrencyInfo()
    }

    override suspend fun update(currencyInfos: List<CurrencyInfo>) {
        executeDaoCall(
            ioDispatcher = ioDispatcher,
            daoCall = {
                currencyDao.insertAll(currencyInfos.map { it.asEntity() })
            }
        )
    }

    override suspend fun delete(currencyInfo: CurrencyInfo) {
        executeDaoCall(
            ioDispatcher = ioDispatcher,
            daoCall = {
                currencyDao.delete(currencyInfo.asEntity())
            }
        )
    }

    override suspend fun list(): List<CurrencyInfo> {
        return executeDaoCall(
            ioDispatcher = ioDispatcher,
            daoCall = {
                currencyDao.all
            }
        ).map { it.asCurrencyInfo() }
    }

    override suspend fun clear() {
        return executeDaoCall(
            ioDispatcher = ioDispatcher,
            daoCall = {
                currencyDao.clear()
            }
        )
    }
}
