package com.xyz.app

import com.xyz.app.ui.common.firstLetterAsUpperCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class ExtensionUnitTest {

    @Test
    fun firstLetterUpperCase_returnFirstLetterOfString() {
        assertEquals("C", "crypto abd".firstLetterAsUpperCase())
    }

    @Test
    fun firstLetterUpperCase_throwExceptionWhenStringIsEmpty() {
        assertThrows<IllegalArgumentException> { "".firstLetterAsUpperCase() }
    }
}