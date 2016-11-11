package com.sf_lolitahag.motions;

import javax.swing.*;

public class Batter extends BaseMotion {

    private static final String[] BATTER_HIT = {"batter01", "batter04", "batter03"};
    private static final String[] BATTER_MISS = {"batter01", "batter02", "batter03"};

    private static final int PAINT_INTERVAL = 200;
    private static final int AXIS_X = 200;
    private static final int AXIS_Y = 475;
    private static final int SHADOW_X = 300;
    private static final int SHADOW_Y = 680;

    private boolean mIsHit = false;
    private int mIndex = 0;
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

    public Batter() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mFileName = BATTER_HIT[mIndex];
        mIsHit = false;
    }

    public int getShadowX() {
        return SHADOW_X;
    }

    public int getShadowY() {
        return SHADOW_Y;
    }

    public void startSwing(boolean isHit) {
        mIsHit = isHit;
        mTimer.start();
    }

    private void updateFileName() {
        if (mIndex >= 2) {
            mTimer.stop();
            finishSwing();
        } else {
            mIndex++;
        }

        mFileName = mIsHit ? BATTER_HIT[mIndex] : BATTER_MISS[mIndex];
    }

    private void finishSwing() {
        mIsHit = false;
        mIndex = 0;
    }
}
