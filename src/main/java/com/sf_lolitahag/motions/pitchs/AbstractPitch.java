package com.sf_lolitahag.motions.pitchs;

public abstract class AbstractPitch {

    protected static final int NONE = 0;

    public abstract int getUpdateX(int currentX);

    public abstract int getUpdateY(int currentY);

    public abstract boolean isSpin();
}
