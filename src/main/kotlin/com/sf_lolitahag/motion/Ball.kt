/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import com.sf_lolitahag.Utils
import com.sf_lolitahag.pitchs.*
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import java.awt.Image
import javax.swing.Timer

class Ball(private val callback: Callback) : AbstractMotion() {

    companion object {
        private const val AXIS_X = 475
        private const val AXIS_Y = 245
        private const val PAINT_INTERVAL = 40
        private const val PAINT_INTERVAL_SPIN = 25
        private const val HIT_ZONE_START = 560
        private const val HIT_ZONE_END = 660
        private val BALL = listOf("ball01")
        private val BALL_SPIN = listOf("spin01", "spin02")
        private val IMAGE_LIST = listOf("ball01", "spin01", "spin02")
    }

    private var index = 0
    private var isHitZone: Boolean = false
    private var pitch: IPitch? = null
    private val spinTimer = Timer(PAINT_INTERVAL_SPIN) { updateSpin() }
    private val paintTimer = Timer(PAINT_INTERVAL) { updateBallView() }

    init {
        cache = cacheManager.createCache(Ball::class.java.simpleName, CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100))
                .build())
        IMAGE_LIST.forEach { name -> cache.put(name, Utils.getImageFromResources(javaClass, name)) }

        init()
    }

    val isHit: Boolean
        get() {
            if (isHitZone) {
                pitch = Liner()
            }

            return isHitZone
        }

    fun startPitch() {
        getPitchRand()
        isShow = true
        paintTimer.start()
    }

    private fun init() {
        axisX = AXIS_X
        axisY = AXIS_Y
        fileName = BALL[index]
        isShow = false
        isHitZone = false
    }

    private fun getPitchRand() {
        pitch = when (BallType.ballType) {
            BallType.STRAIGHT -> Straight()
            BallType.FAST -> Fast()
            BallType.MAKYU -> Makyu()
        }
    }

    private fun updateBallView() {
        val updateX = pitch!!.getUpdateX(axisX)
        axisX += updateX
        val updateY = pitch!!.getUpdateY(axisY)
        axisY += updateY
        isHitZone = axisY in HIT_ZONE_START..HIT_ZONE_END

        if (pitch!!.isSpin) {
            spinTimer.start()
        } else {
            spinTimer.stop()
            index = 0
            fileName = BALL[index]
        }

        checkFinish(updateY)
    }

    private fun checkFinish(updateY: Int) {
        val axisY = this.axisY + updateY
        if (axisY < AXIS_Y || axisY >= 800) {
            paintTimer.stop()
            callback.onFinishPitch(axisY >= 800)
            init()
        }
    }

    private fun updateSpin() {
        if (index == 0) {
            index = 1
        } else if (index == 1) {
            index = 0
        }
        fileName = BALL_SPIN[index]
    }

    private enum class BallType {
        STRAIGHT,
        FAST,
        MAKYU;


        companion object {

            val ballType: BallType
                get() {
                    val index = (Math.random() * BallType.values().size).toInt()
                    return getBallTypeByIndex(index)
                }

            private fun getBallTypeByIndex(index: Int): BallType {
                val objects = listOf(*BallType.values())
                return objects[index]
            }
        }
    }

    interface Callback {

        fun onFinishPitch(isWin: Boolean)
    }
}
