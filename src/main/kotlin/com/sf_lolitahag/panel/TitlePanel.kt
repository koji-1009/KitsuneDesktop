/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel

import com.sf_lolitahag.Utils
import java.awt.Graphics

class TitlePanel : AbstractPanel() {

    private val TITLE = "title"

    override fun paintComponent(g: Graphics) {
        g.drawImage(Utils.getImageFromResources(javaClass, TITLE), 0, 0, null)
    }
}
