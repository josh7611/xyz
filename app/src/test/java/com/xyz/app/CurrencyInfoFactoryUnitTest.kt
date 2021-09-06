package com.xyz.app

import com.xyz.app.domain.CurrencyInfo
import com.xyz.app.ui.main.CurrencyInfoFactory
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CurrencyInfoFactoryUnitTest {

    @Test
    fun list_shouldReturnsCurrencyInfos() {
        assertEquals(14, CurrencyInfoFactory.list().size)
        assertEquals(
            CurrencyInfo(
                id = "BTC",
                name = "Bitcoin",
                symbol = "BTC"
            ), CurrencyInfoFactory.list()[0]
        )
    }
}