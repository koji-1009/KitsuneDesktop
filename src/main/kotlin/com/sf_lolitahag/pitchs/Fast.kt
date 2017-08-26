/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.pitchs

class Fast : IPitch {

    private val BALL_SPEED = 40

    override fun getUpdateX(currentX: Int): Int = IPitch.NONE

    override fun getUpdateY(currentY: Int): Int = BALL_SPEED

    override val isSpin: Boolean
        get() = false
}
