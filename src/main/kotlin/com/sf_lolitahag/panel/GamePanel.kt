/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel

import com.sf_lolitahag.Utils
import com.sf_lolitahag.motion.Ball
import com.sf_lolitahag.motion.Batter
import com.sf_lolitahag.motion.IMotion
import com.sf_lolitahag.motion.Kitsune
import java.awt.Graphics
import javax.swing.Timer

class GamePanel : AbstractPanel(), Kitsune.Callback, Ball.Callback {

    private val BACK = "back"
    private val PAINT_INTERVAL = 30

    private val image = Utils.getImageFromResources(javaClass, BACK)
    private val kitsune = Kitsune(this)
    private val batter = Batter()
    private val ball = Ball(this)
    private val motions = listOf<IMotion>(kitsune, batter, ball)

    init {
        Timer(PAINT_INTERVAL) { repaint() }.start()
        setOnClickListener(listener = startSwing())
    }

    override fun paintComponent(g: Graphics) {
        g.drawImage(image, 0, 0, null)
        motions.forEach {
            if (it.isShow) {
                g.drawImage(it.shadowImage, it.axisShadowX, it.axisShadowY, null)
                g.drawImage(it.bodyImage, it.axisX, it.axisY, null)
            }
        }
    }

    override fun onFinishThrow() {
        ball.startPitch()
    }

    override fun onFinishPitch(isWin: Boolean) {
        kitsune.showWinOrLosePose(isWin)
    }

    private fun startSwing() = object : AbstractPanel.ClickListener {
        override fun onClick() {
            batter.startSwing(ball.isHit)
        }
    }


}
