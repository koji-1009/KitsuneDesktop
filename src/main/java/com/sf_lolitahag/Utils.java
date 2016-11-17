package com.sf_lolitahag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Utils {

    public static BufferedImage getImageFromResources(final Class Class, final String fileName) {
        if (fileName == null || fileName.equals("")) {
            return null;
        }

        try {
            return ImageIO.read(Class.getResource("/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
