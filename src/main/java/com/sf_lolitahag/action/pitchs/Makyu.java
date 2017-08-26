/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.action.pitchs;

public class Makyu implements IPitch {

  private static final int BALL_SPEED = 20;
  private static final int ACCELERATION = 20;
  private static final int STOP_POSITION = 400;
  private static final long STOP_TIME = 1000;
  private boolean isSpin;
  private long postTime;
  private int ballSpeed;

  public Makyu() {
    isSpin = true;
    postTime = NONE;
    ballSpeed = BALL_SPEED;
  }

  @Override
  public int getUpdateX(int currentX) {
    return NONE;
  }

  @Override
  public int getUpdateY(int currentY) {
    long currentTime = System.currentTimeMillis();
    if (currentY >= STOP_POSITION && (postTime == NONE || currentTime - postTime < STOP_TIME)) {
      if (postTime == NONE) {
        postTime = currentTime;
        ballSpeed += ACCELERATION;
      }
      isSpin = true;
      return NONE;
    } else {
      isSpin = false;
    }
    return ballSpeed;
  }

  @Override
  public boolean isSpin() {
    return isSpin;
  }
}
