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

class Kitsune(private val callback: Callback) : AbstractMotion() {

    companion object {
        private const val AXIS_X = 400
        private const val AXIS_Y = 100
        private const val SHADOW_X = 400
        private const val SHADOW_Y = 300
        private const val PAINT_INTERVAL = 400
        private val THROW_ANIMATION = intArrayOf(0, 1, 0, 1, 2, 3)
        private val WIN_ANIMATION = intArrayOf(0, 1, 0, 1, 0, 1)
        private val LOSE_ANIMATION = intArrayOf(0)
        private const val SHADOW = "shadow01"
        private val KITSUNE_LOSE = listOf("mae_ga_mienee")
        private val KITSUNE_THROW = listOf("stay01", "stay02", "throw01", "throw02")
        private val KITSUNE_WIN = listOf("nojanoja01", "nojanoja02", "nojanoja01", "nojanoja02")
        private val IMAGE_LIST = listOf("shadow01", "stay01", "stay02", "throw01", "throw02",
                "nojanoja01", "nojanoja02", "mae_ga_mienee")
    }

    private var index = 0
    private var mode = MODE.THROW
    private val timer = Timer(PAINT_INTERVAL) { updateConfig() }

    init {
        axisX = AXIS_X
        axisY = AXIS_Y
        axisShadowX = SHADOW_X
        axisShadowY = SHADOW_Y
        fileNameShadow = SHADOW

        cache = cacheManager.createCache(Kitsune::class.java.simpleName, CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100))
                .build())
        IMAGE_LIST.forEach { name -> cache.put(name, Utils.getImageFromResources(javaClass, name)) }
        updateFileName()
        startThrow()
    }

    fun showWinOrLosePose(isWin: Boolean) {
        mode = if (isWin) {
            MODE.WIN
        } else {
            MODE.LOSE
        }
        updateFileName()
        timer.start()
    }

    private fun checkAnimationFinish() {
        when (mode) {
            MODE.THROW -> if (index >= THROW_ANIMATION.size) {
                timer.stop()
                index = 0
                callback.onFinishThrow()
            }
            MODE.WIN -> if (index >= WIN_ANIMATION.size) {
                timer.stop()
                index = 0
                startThrow()
            }
            MODE.LOSE -> if (index >= LOSE_ANIMATION.size) {
                timer.stop()
                index = 0
                startThrow()
            }
        }
    }

    private fun startThrow() {
        mode = MODE.THROW
        timer.start()
    }

    private fun updateConfig() {
        updateFileName()
        index++
        checkAnimationFinish()
    }

    private fun updateFileName() {
        fileName = when (mode) {
            MODE.THROW -> KITSUNE_THROW[THROW_ANIMATION[index]]
            MODE.WIN -> KITSUNE_WIN[WIN_ANIMATION[index]]
            MODE.LOSE -> KITSUNE_LOSE[LOSE_ANIMATION[index]]
        }
    }

    private enum class MODE {
        THROW,
        WIN,
        LOSE
    }

    interface Callback {

        fun onFinishThrow()
    }
}
