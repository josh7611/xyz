/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xyz.app.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xyz.app.data.db.ExchangeDataBase
import com.xyz.app.data.db.dao.CurrencyDao
import com.xyz.app.data.db.entity.asEntity
import com.xyz.app.domain.CurrencyInfo
import junit.framework.Assert.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CurrencyDbTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: ExchangeDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExchangeDataBase::class.java
        ).build()
        currencyDao = db.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun createCurrencyInfos() = run {
        listOf(
            CurrencyInfo(
                "XRP",
                "XRP",
                "XRP"
            ), CurrencyInfo(
                "BCH",
                "Bitcoin Cash",
                "BCH"
            ))
    }

    @Test
    @Throws(Exception::class)
    fun writeCurrencyInfoAndRead() = runBlocking {
        val currencyInfo = createCurrencyInfos().first()

        currencyDao.insertAll(listOf(currencyInfo.asEntity()))

        val localObj = currencyDao.read(currencyInfo.id)
        assertEquals(currencyInfo, localObj.asCurrencyInfo())
    }

    @Test
    @Throws(Exception::class)
    fun deleteCamera() = runBlocking {
        val currencyInfo = createCurrencyInfos().first()
        currencyDao.insertAll(listOf(currencyInfo.asEntity()))
        currencyDao.delete(currencyInfo.asEntity())

        val localObj = currencyDao.read(currencyInfo.id)
        assertNull(localObj)
    }

    @Test
    @Throws(Exception::class)
    fun readAll() = runBlocking {
        val currencyInfos = createCurrencyInfos()

        currencyDao.insertAll(currencyInfos.map { it.asEntity() })

        val firstEmit = currencyDao.readAllAsFlow().first()
        assertEquals(2, firstEmit.size)
        assertEquals(currencyInfos[0], firstEmit[0].asCurrencyInfo())
        assertEquals(currencyInfos[1], firstEmit[1].asCurrencyInfo())
    }

    @Test
    @Throws(Exception::class)
    fun clear() = runBlocking {
        val currencyInfos = createCurrencyInfos()

        currencyDao.insertAll(currencyInfos.map { it.asEntity() })
        assertEquals(2, currencyDao.all.size)

        currencyDao.clear()

        assertTrue(currencyDao.all.isEmpty())
    }
}
