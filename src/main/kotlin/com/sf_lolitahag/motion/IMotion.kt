package com.sf_lolitahag.motion

import java.awt.Image

interface IMotion {

    val axisX: Int

    val axisY: Int

    val axisShadowX: Int

    val axisShadowY: Int

    val bodyImage: Image?

    val shadowImage: Image?

    val isShow: Boolean
}
