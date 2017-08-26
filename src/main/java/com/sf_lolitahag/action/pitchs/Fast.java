/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.action.pitchs;

public class Fast implements IPitch {

  private static final int BALL_SPEED = 40;

  @Override
  public int getUpdateX(int currentX) {
    return NONE;
  }

  @Override
  public int getUpdateY(int currentY) {
    return BALL_SPEED;
  }

  @Override
  public boolean isSpin() {
    return false;
  }
}
