/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
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
