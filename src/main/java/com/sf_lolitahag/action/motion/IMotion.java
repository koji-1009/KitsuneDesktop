package com.sf_lolitahag.action.motion;

import java.awt.Image;

public interface IMotion {

  int getAxisX();

  int getAxisY();

  int getAxisShadowX();

  int getAxisShadowY();

  Image getBodyImage();

  Image getShadowImage();

  boolean isShow();
}
