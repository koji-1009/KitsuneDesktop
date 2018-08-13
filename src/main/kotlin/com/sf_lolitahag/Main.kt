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

object Main {

    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 800
    private const val FRAME_TITLE = "ロリのバーさんのホームランダービー"
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
            addKeyListener(key())
        }

        panel = TitlePanel()
        panel.setOnClickListener(changePanel())
        frame.add(panel)

        frame.isVisible = true
    }

    private fun key() = object : KeyListener {
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
    }

    private fun changePanel() = object : AbstractPanel.ClickListener {
        override fun onClick() {
            frame.remove(panel)
            panel = GamePanel()
            frame.run {
                add(panel)
                isVisible = true
            }
        }
    }
}
