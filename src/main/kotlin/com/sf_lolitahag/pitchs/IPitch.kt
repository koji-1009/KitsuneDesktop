package com.sf_lolitahag.pitchs

interface IPitch {

    fun getUpdateX(currentX: Int): Int

    fun getUpdateY(currentY: Int): Int

    val isSpin: Boolean

    companion object {

        val NONE = 0
    }
}
