package com.sf_lolitahag.pitchs

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class StraightTest {
    private val straight = Straight()

    @Test
    fun getUpdateX() {
        assertEquals(straight.getUpdateX(0), IPitch.NONE)
    }

    @Test
    fun getUpdateY() {
        assertEquals(straight.getUpdateY(0), 20)
    }

    @Test
    fun isSpin() {
        assertFalse { straight.isSpin }

    }

}