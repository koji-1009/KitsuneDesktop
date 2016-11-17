package com.sf_lolitahag.motions;

import javax.swing.*;

public class Batter extends BaseMotion {

    private static final int AXIS_X = 200;
    private static final int AXIS_Y = 475;
    private static final int SHADOW_X = 300;
    private static final int SHADOW_Y = 680;
    private static final int PAINT_INTERVAL = 85;
    private static final String SHADOW = "shadow01";
    private static final String[] BATTER_MISS = {"batter01", "batter04", "batter03", "batter01"};
    private static final String[] BATTER_HIT = {"batter01", "batter02", "batter03", "batter01"};
    private int mIndex;
    private boolean mIsHit;
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

    public Batter() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mAxisShadowX = SHADOW_X;
        mAxisShadowY = SHADOW_Y;
        mFileNameShadow = SHADOW;

        mIndex = 0;
        mFileName = BATTER_HIT[mIndex];
        mIsHit = false;
    }

    public void startSwing(boolean isHit) {
        mIsHit = isHit;
        mTimer.start();
    }

    private void updateFileName() {
        mIndex++;
        mFileName = mIsHit ? BATTER_HIT[mIndex] : BATTER_MISS[mIndex];
        if (mIndex >= 3) {
            mTimer.stop();
            mIndex = 0;
            mIsHit = false;
        }
    }
}
