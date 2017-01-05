package com.sf_lolitahag.motions;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.motions.pitchs.*;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * ボール実装
 */
public class Ball extends AbstractMotion {

    private static final int AXIS_X = 475;
    private static final int AXIS_Y = 245;
    private static final int PAINT_INTERVAL = 20;
    private static final int PAINT_INTERVAL_SPIN = 25;
    private static final int HIT_ZONE_START = 520;
    private static final int HIT_ZONE_END = 600;
    private static final String[] BALL = {"ball01"};
    private static final String[] BALL_SPIN = {"spin01", "spin02"};
    private static final String[] IMAGE_LIST = {"ball01", "spin01", "spin02"};
    private int mIndex = 0;
    private boolean mIsHitZone;
    private Callback mCallback;
    private AbstractPitch mPitch;
    private Timer mSpinTimer = new Timer(PAINT_INTERVAL_SPIN, (e) -> updateSpin());
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateBallView());

    public Ball(Callback callback) {
        mCallback = callback;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache(Ball.class.getSimpleName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

        init();
    }

    public void startPitch() {
        getPitchRand();
        mIsShow = true;
        mTimer.start();
    }

    public boolean isHit() {
        if (mIsHitZone) {
            mPitch = new Liner();
        }

        return mIsHitZone;
    }

    private void init() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mFileName = BALL[mIndex];
        mIsShow = false;
        mIsHitZone = false;
    }

    private void getPitchRand() {
        switch (BallType.getBallType()) {
            case STRAIGHT:
                mPitch = new Straight();
                break;
            case FAST:
                mPitch = new Fast();
                break;
            case MAKYU:
                mPitch = new Makyu();
                break;
        }
    }

    private void updateBallView() {
        int updateX = mPitch.getUpdateX(mAxisX);
        mAxisX += updateX;
        int updateY = mPitch.getUpdateY(mAxisY);
        mAxisY += updateY;
        mIsHitZone = mAxisY >= HIT_ZONE_START && mAxisY <= HIT_ZONE_END;

        if (mPitch.isSpin()) {
            mSpinTimer.start();
        } else {
            mSpinTimer.stop();
            mIndex = 0;
            mFileName = BALL[mIndex];
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

    private void updateSpin() {
        if (mIndex == 0) {
            mIndex = 1;
        } else if (mIndex == 1) {
            mIndex = 0;
        }
        mFileName = BALL_SPIN[mIndex];
    }

    private enum BallType {
        STRAIGHT,
        FAST,
        MAKYU;

        public static BallType getBallType() {
            int index = (int) (Math.random() * BallType.values().length);
            return getBallTypeByIndex(index);
        }

        private static BallType getBallTypeByIndex(int index) {
            List<BallType> objects = Arrays.asList(BallType.values());
            return objects.get(index);
        }
    }

    public interface Callback {
        void onFinishPitch(boolean isWin);
    }
}
