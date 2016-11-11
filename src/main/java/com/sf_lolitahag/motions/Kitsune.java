package com.sf_lolitahag.motions;

import javax.swing.*;

public class Kitsune extends BaseMotion {

    private static final int AXIS_X = 400;
    private static final int AXIS_Y = 100;
    private static final String[] KITSUNE_LOSE = {"mae_ga_mienee"};
    private static final String[] KITSUNE_THROW = {"stay01", "stay02", "throw01", "throw02"};
    private static final String[] KITSUNE_WIN = {"nojanoja01", "nojanoja02", "nojanoja01", "nojanoja02"};
    private static final int PAINT_INTERVAL = 400;
    private static final int SHADOW_X = 400;
    private static final int SHADOW_Y = 300;
    private static final int[] THROW_ANIMATION = {0, 1, 0, 1, 2, 3};
    private static final int[] WIN_ANIMATION = {0, 1, 0, 1, 0, 1};
    private static final int[] LOSE_ANIMATION = {0};
    private int mIndex = 0;
    private MODE mMode;
    private Timer mResultTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());
    private Timer mBallCheckTimer = new Timer(PAINT_INTERVAL, (e) -> checkBall());
    private Timer mThrowTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

    public Kitsune() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mFileName = KITSUNE_THROW[mIndex];
        mMode = MODE.THROW;
        mThrowTimer.start();
    }

    public int getShadowX() {
        return SHADOW_X;
    }

    public int getShadowY() {
        return SHADOW_Y;
    }

    private void updateFileName() {
        switch (mMode) {
            case THROW:
                mFileName = KITSUNE_THROW[THROW_ANIMATION[mIndex]];
                mIndex++;
                if (mIndex >= THROW_ANIMATION.length) {
                    mThrowTimer.stop();
                    mIndex = 0;
                    mBallCheckTimer.start();
                }
                break;
            case WIN:
                mFileName = KITSUNE_WIN[WIN_ANIMATION[mIndex]];
                mIndex++;
                if (mIndex >= WIN_ANIMATION.length) {
                    mResultTimer.stop();
                    mIndex = 0;
                    mMode = MODE.THROW;
                    mThrowTimer.start();
                }
                break;
            case LOSE:
                mFileName = KITSUNE_LOSE[LOSE_ANIMATION[mIndex]];
                mIndex++;
                if (mIndex >= LOSE_ANIMATION.length) {
                    mResultTimer.stop();
                    mIndex = 0;
                    mMode = MODE.THROW;
                    mThrowTimer.start();
                }
                break;
        }
    }

    private void checkBall() {
        // ボールの状態のチェックをする

        mBallCheckTimer.stop();
        // キツネの勝ちなら
        mMode = MODE.WIN;
        mResultTimer.start();
    }

    private enum MODE {
        THROW,
        WIN,
        LOSE
    }
}
