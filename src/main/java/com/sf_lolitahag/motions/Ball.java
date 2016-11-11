package com.sf_lolitahag.motions;

public class Ball extends BaseMotion {

    private static final String[] BALL = {"ball01", "spin01", "spin02"};
    private static final int AXIS_X = 475;
    private static final int AXIS_Y = 245;

    public Ball() {
        initBallPosition();
    }

    private void initBallPosition(){
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mFileName = BALL[0];
        mIsShow = false;
    }
}
