package com.sf_lolitahag.motions.pitchs;

public class Makyu extends AbstractPitch {

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
