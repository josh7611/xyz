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

package com.xyz.app.data.repository

import com.xyz.app.data.data_source.CurrencyInfoLocalDataSource
import com.xyz.app.domain.CurrencyInfo

class CurrencyInfoRepository(
    private val currencyInfoLocalDataSource: CurrencyInfoLocalDataSource
) {

    suspend fun read(currencyId: String): CurrencyInfo {
        TODO("Not yet implemented")
    }
    
    suspend fun update(currencyInfos: List<CurrencyInfo>) {
        currencyInfoLocalDataSource.update(currencyInfos)
    }

    suspend fun delete(currencyInfo: CurrencyInfo) {
        TODO("Not yet implemented")
    }

    suspend fun list(): List<CurrencyInfo> {
        return currencyInfoLocalDataSource.list()
    }
}
