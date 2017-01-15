package com.sf_lolitahag.motions.pitchs;

/**
 * 様々な球種のBaseクラス 次にどれだけ X軸、Y軸で進むのか実装する
 */
public abstract class AbstractPitch {

  protected static final int NONE = 0;

  public abstract int getUpdateX(int currentX);

  public abstract int getUpdateY(int currentY);

  public abstract boolean isSpin();
}
