package com.sf_lolitahag.motions;

public class BallState {

    private static BallState ourInstance = new BallState();
    private boolean mIsHit;

    private BallState() {
    }

    public static BallState getInstance() {
        return ourInstance;
    }

    public boolean isHit() {
        return mIsHit;
    }

    public void setHit(boolean isHit) {
        mIsHit = isHit;
    }
}
