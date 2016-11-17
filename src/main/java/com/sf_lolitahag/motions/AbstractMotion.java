package com.sf_lolitahag.motions;

/**
 * パネル上で表示するオブジェクトのBaseクラス
 * 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
public abstract class AbstractMotion {

    protected int mAxisX = 0;
    protected int mAxisY = 0;
    protected int mAxisShadowX = 0;
    protected int mAxisShadowY = 0;
    protected String mFileName = "";
    protected String mFileNameShadow = "";
    protected boolean mIsShow = true;

    public final int getAxisX() {
        return mAxisX;
    }

    public final int getAxisY() {
        return mAxisY;
    }

    public final int getAxisShadowX() {
        return mAxisShadowX;
    }

    public final int getAxisShadowY() {
        return mAxisShadowY;
    }

    public final String getFileName() {
        return mFileName;
    }

    public final String getFileNameShadow() {
        return mFileNameShadow;
    }

    public final boolean isShow() {
        return mIsShow;
    }
}
