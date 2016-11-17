package com.sf_lolitahag.motions.pitchs;

public class Straight extends AbstractPitch {

    private static final int BALL_SPEED = 20;

    @Override
    public int getUpdateX(int currentX) {
        return NONE;
    }

    @Override
    public int getUpdateY(int currentY) {
        return BALL_SPEED;
    }

    @Override
    public boolean isSpin() {
        return false;
    }
}
