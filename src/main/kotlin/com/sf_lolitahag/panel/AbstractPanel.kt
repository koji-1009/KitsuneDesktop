/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel

import javax.swing.JPanel

/**
 * 表示するパネルのBaseクラス SpaceKeyを押した場合の処理を継承クラスで実装する
 */
abstract class AbstractPanel : JPanel() {

    private var listener: ClickListener? = null

    fun onSpaceKeyPress() {
        listener?.onClick()
    }

    fun setOnClickListener(listener: ClickListener) {
        this.listener = listener
    }

    interface ClickListener {
        fun onClick()
    }
}
