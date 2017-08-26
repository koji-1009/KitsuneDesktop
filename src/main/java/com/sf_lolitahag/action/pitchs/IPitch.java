package com.sf_lolitahag.action.pitchs;

public interface IPitch {

  int NONE = 0;

  int getUpdateX(int currentX);

  int getUpdateY(int currentY);

  boolean isSpin();
}
