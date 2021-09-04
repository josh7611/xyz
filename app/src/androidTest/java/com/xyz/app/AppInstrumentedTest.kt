package com.xyz.app

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xyz.app.data.repository.CurrencyInfoRepository
import com.xyz.app.infrastructure.SimpleDi

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.xyz.app", appContext.packageName)
    }

    @Test
    fun testDi() {
        val repository: CurrencyInfoRepository by SimpleDi
        assertNotNull(repository)
    }
}