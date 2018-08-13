/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.pitchs

class Makyu : IPitch {

    companion object {

        private const val BALL_SPEED = 20
        private const val ACCELERATION = 20
        private const val STOP_POSITION = 400
        private const val STOP_TIME = 1000L
    }

    override var isSpin: Boolean = false
        private set
    private var postTime: Long = 0
    private var ballSpeed: Int = 0

    init {
        isSpin = true
        postTime = IPitch.NONE.toLong()
        ballSpeed = BALL_SPEED
    }

    override fun getUpdateX(currentX: Int): Int {
        return IPitch.NONE
    }

    override fun getUpdateY(currentY: Int): Int {
        val currentTime = System.currentTimeMillis()
        if (currentY >= STOP_POSITION && (postTime == IPitch.NONE.toLong() || currentTime - postTime < STOP_TIME)) {
            if (postTime == IPitch.NONE.toLong()) {
                postTime = currentTime
                ballSpeed += ACCELERATION
            }
            isSpin = true
            return IPitch.NONE
        } else {
            isSpin = false
        }
        return ballSpeed
    }
}
