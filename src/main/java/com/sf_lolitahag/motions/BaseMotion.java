package com.sf_lolitahag.motions;

public abstract class BaseMotion {

    protected int mAxisX = 0;
    protected int mAxisY = 0;
    protected String mFileName = "";
    protected boolean mIsShow = true;

    public final int getAxisX() {
        return mAxisX;
    }

    public final int getAxisY() {
        return mAxisY;
    }

    public final String getFileName() {
        return mFileName;
    }

    public final boolean isShow() {
        return mIsShow;
    }
}
