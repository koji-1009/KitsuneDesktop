package com.sf_lolitahag.motions;

import com.sf_lolitahag.motions.pitchs.AbstractPitch;
import com.sf_lolitahag.motions.pitchs.Straight;
import com.sf_lolitahag.motions.pitchs.Struck;

import javax.swing.*;

public class Ball extends BaseMotion {

    private static final int AXIS_X = 475;
    private static final int AXIS_Y = 245;
    private static final int PAINT_INTERVAL = 20;
    private static final int HIT_ZONE_START = 500;
    private static final int HIT_ZONE_END = 600;
    private static final String[] BALL = {"ball01"};
    private static final String[] BALL_SPIN = {"spin01", "spin02"};
    private int mIndex = 0;
    private long mStartTime;
    private Callback mCallback;
    private AbstractPitch mPitch;
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updatePosition());

    public Ball(Callback callback) {
        mCallback = callback;
        init();
    }

    private void init() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mIndex = 0;
        mFileName = BALL[mIndex];
        mIsShow = false;
    }

    public void startPitch() {
        getPitchRand();
        mIsShow = true;
        mStartTime = System.currentTimeMillis();
        mTimer.start();
    }

    private void getPitchRand() {
        mPitch = new Straight();
    }

    public void isHit(boolean isHit) {
        if (isHit) {
            mPitch = new Struck();
        }
    }

    private void updatePosition() {
        int updateX = mPitch.getUpdateX(mStartTime);
        mAxisX += updateX;
        int updateY = mPitch.getUpdateY(mStartTime);
        mAxisY += updateY;
        // mPitch.isSpin();
        if (mAxisY >= HIT_ZONE_START && mAxisY <= HIT_ZONE_END) {
            BallState.getInstance().setHit(true);
        } else {
            BallState.getInstance().setHit(false);
        }

        checkFinish(updateY);
    }

    private void checkFinish(int updateY) {
        int axisY = mAxisY + updateY;
        if (axisY < AXIS_Y || axisY >= 800) {
            mTimer.stop();
            mCallback.onFinishPitch(axisY >= 800);
            init();
        }
    }

    public interface Callback {
        void onFinishPitch(boolean isWin);
    }
}
