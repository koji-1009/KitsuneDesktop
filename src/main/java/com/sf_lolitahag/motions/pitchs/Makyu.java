package com.sf_lolitahag.motions.pitchs;

public class Makyu extends AbstractPitch {

    private static final int BALL_SPEED = 20;
    private static final int ACCELERATION = 20;
    private static final int STOP_POSITION = 400;
    private static final long STOP_TIME = 1000;
    private boolean mIsSpin;
    private long mPoseTime;
    private int mBallSpeed;

    public Makyu() {
        mIsSpin = true;
        mPoseTime = NONE;
        mBallSpeed = BALL_SPEED;
    }

    @Override
    public int getUpdateX(int currentX) {
        return NONE;
    }

    @Override
    public int getUpdateY(int currentY) {
        long currentTime = System.currentTimeMillis();
        if (currentY >= STOP_POSITION && (mPoseTime == NONE || currentTime - mPoseTime < STOP_TIME)) {
            if (mPoseTime == NONE) {
                mPoseTime = currentTime;
                mBallSpeed += ACCELERATION;
            }
            mIsSpin = true;
            return NONE;
        } else {
            mIsSpin = false;
        }
        return mBallSpeed;
    }

    @Override
    public boolean isSpin() {
        return mIsSpin;
    }
}
