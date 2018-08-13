/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import com.sf_lolitahag.Utils
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import java.awt.Image
import javax.swing.Timer

class Batter : AbstractMotion() {

    companion object {
        private const val AXIS_X = 200
        private const val AXIS_Y = 475
        private const val SHADOW_X = 300
        private const val SHADOW_Y = 680
        private const val PAINT_INTERVAL = 100
        private const val SHADOW = "shadow01"
        private val BATTER_MISS = listOf("batter01", "batter04", "batter03", "batter01")
        private val BATTER_HIT = listOf("batter01", "batter02", "batter03", "batter01")
        private val IMAGE_LIST = listOf("shadow01", "batter01", "batter02", "batter03", "batter04")
    }

    private var index = 0
    private var isHit: Boolean = false
    private val timer = Timer(PAINT_INTERVAL) { updateFileName() }

    init {
        axisX = AXIS_X
        axisY = AXIS_Y
        axisShadowX = SHADOW_X
        axisShadowY = SHADOW_Y
        fileNameShadow = SHADOW

        cache = cacheManager.createCache(Batter::class.java.simpleName, CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100))
                .build())
        IMAGE_LIST.forEach { name -> cache.put(name, Utils.getImageFromResources(javaClass, name)) }

        fileName = BATTER_HIT[index]
        isHit = false
    }

    fun startSwing(isHit: Boolean) {
        this.isHit = isHit
        timer.start()
    }

    private fun updateFileName() {
        index++
        fileName = if (isHit) BATTER_HIT[index] else BATTER_MISS[index]
        if (index >= 3) {
            timer.stop()
            index = 0
            isHit = false
        }
    }
}
