package com.sf_lolitahag.motions;

import com.sf_lolitahag.Utils;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * きつね（ロリババア）実装
 */
public class Kitsune extends AbstractMotion {

    private static final int AXIS_X = 400;
    private static final int AXIS_Y = 100;
    private static final int SHADOW_X = 400;
    private static final int SHADOW_Y = 300;
    private static final int PAINT_INTERVAL = 400;
    private static final int[] THROW_ANIMATION = {0, 1, 0, 1, 2, 3};
    private static final int[] WIN_ANIMATION = {0, 1, 0, 1, 0, 1};
    private static final int[] LOSE_ANIMATION = {0};
    private static final String SHADOW = "shadow01";
    private static final String[] KITSUNE_LOSE = {"mae_ga_mienee"};
    private static final String[] KITSUNE_THROW = {"stay01", "stay02", "throw01", "throw02"};
    private static final String[] KITSUNE_WIN = {"nojanoja01", "nojanoja02", "nojanoja01", "nojanoja02"};
    private static final String[] IMAGE_LIST = {"shadow01", "stay01", "stay02", "throw01", "throw02", "nojanoja01", "nojanoja02", "mae_ga_mienee"};
    private int mIndex = 0;
    private MODE mMode = MODE.THROW;
    private Callback mCallback;
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateConfig());

    public Kitsune(Callback callback) {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mAxisShadowX = SHADOW_X;
        mAxisShadowY = SHADOW_Y;
        mFileNameShadow = SHADOW;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache("kitsune",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

        mCallback = callback;
        updateFileName();
        startThrow();
    }

    public void showWinOrLosePose(boolean isWin) {
        if (isWin) {
            mMode = MODE.WIN;
        } else {
            mMode = MODE.LOSE;
        }
        updateFileName();
        mTimer.start();
    }

    private void checkAnimationFinish() {
        switch (mMode) {
            case THROW:
                if (mIndex >= THROW_ANIMATION.length) {
                    mTimer.stop();
                    mIndex = 0;
                    mCallback.onFinishThrow();
                }
                break;
            case WIN:
                if (mIndex >= WIN_ANIMATION.length) {
                    mTimer.stop();
                    mIndex = 0;
                    startThrow();
                }
                break;
            case LOSE:
                if (mIndex >= LOSE_ANIMATION.length) {
                    mTimer.stop();
                    mIndex = 0;
                    startThrow();
                }
                break;
        }

    }

    private void startThrow() {
        mMode = MODE.THROW;
        mTimer.start();
    }

    private void updateConfig() {
        updateFileName();
        mIndex++;
        checkAnimationFinish();
    }

    private void updateFileName() {
        switch (mMode) {
            case THROW:
                mFileName = KITSUNE_THROW[THROW_ANIMATION[mIndex]];
                break;
            case WIN:
                mFileName = KITSUNE_WIN[WIN_ANIMATION[mIndex]];
                break;
            case LOSE:
                mFileName = KITSUNE_LOSE[LOSE_ANIMATION[mIndex]];
                break;
        }
    }

    private enum MODE {
        THROW,
        WIN,
        LOSE
    }

    public interface Callback {
        void onFinishThrow();
    }
}
