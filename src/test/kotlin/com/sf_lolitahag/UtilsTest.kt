package com.sf_lolitahag

import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UtilsTest {

    @Test
    fun getImageFromResources() {
        assertNull(Utils.getImageFromResources(javaClass, null))
        assertNull(Utils.getImageFromResources(javaClass, ""))

        assertNotNull(Utils.getImageFromResources(javaClass, "back"))
    }

}