package com.sf_lolitahag.motions.pitchs;

public abstract class AbstractPitch {

    protected static final int NONE = 0;
    protected boolean mIsSpin = false;
    protected int mUpdateX = NONE;
    protected int mUpdateY = NONE;

    public int getUpdateX(long startTime) {
        return mUpdateX;
    }

    public int getUpdateY(long startTime) {
        return mUpdateY;
    }

    public boolean isSpin() {
        return mIsSpin;
    }
}
