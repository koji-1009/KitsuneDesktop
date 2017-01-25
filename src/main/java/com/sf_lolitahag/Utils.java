/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Utils {

  public static BufferedImage getImageFromResources(final Class Class, final String fileName) {
    if (fileName == null || fileName.equals("")) {
      return null;
    }

    BufferedImage image = null;
    try {
      image = ImageIO.read(Class.getResource("/" + fileName + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (image == null) {
      System.err.println(fileName + " is not found. return null.");
    }
    return image;
  }
}
