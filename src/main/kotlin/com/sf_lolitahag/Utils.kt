/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

object Utils {

    fun getImageFromResources(Class: Class<*>, fileName: String?): BufferedImage? {
        if (fileName == null || fileName == "") {
            return null
        }

        var image: BufferedImage? = null
        try {
            image = ImageIO.read(Class.getResource("/$fileName.png"))
        } catch (e: IOException) {
            e.printStackTrace()
            System.err.println(fileName + " is not found. return null.")
        }

        return image
    }
}
