/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag

import com.sf_lolitahag.panel.AbstractPanel
import com.sf_lolitahag.panel.GamePanel
import com.sf_lolitahag.panel.TitlePanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

object Main : KeyListener, TitlePanel.Callback {

    private val FRAME_WIDTH = 1000
    private val FRAME_HEIGHT = 800
    private val FRAME_TITLE = "ロリのバーさんのホームランダービー"
    private lateinit var frame: JFrame
    private lateinit var panel: AbstractPanel

    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater { initFrame() }
    }

    private fun initFrame() {
        frame = JFrame(FRAME_TITLE).apply {
            setSize(FRAME_WIDTH, FRAME_HEIGHT)
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            addKeyListener(this@Main)
        }

        panel = TitlePanel(this@Main)
        frame.add(panel)

        frame.isVisible = true
    }

    override fun keyTyped(e: KeyEvent) {
        // nop
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_SPACE -> panel.onSpaceKeyPress()
        }
    }

    override fun keyReleased(e: KeyEvent) {
        // nop
    }

    override fun gotoGamePanel() {
        frame.remove(panel)
        panel = GamePanel()
        frame.run {
            add(panel)
            isVisible = true
        }
    }
}
