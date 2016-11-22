package com.sf_lolitahag.motions;

import com.sf_lolitahag.Utils;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * バッター実装
 */
public class Batter extends AbstractMotion {

    private static final int AXIS_X = 200;
    private static final int AXIS_Y = 475;
    private static final int SHADOW_X = 300;
    private static final int SHADOW_Y = 680;
    private static final int PAINT_INTERVAL = 85;
    private static final String SHADOW = "shadow01";
    private static final String[] BATTER_MISS = {"batter01", "batter04", "batter03", "batter01"};
    private static final String[] BATTER_HIT = {"batter01", "batter02", "batter03", "batter01"};
    private static final String[] IMAGE_LIST = {"shadow01", "batter01", "batter02", "batter03", "batter04"};
    private int mIndex = 0;
    private boolean mIsHit;
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

    public Batter() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;
        mAxisShadowX = SHADOW_X;
        mAxisShadowY = SHADOW_Y;
        mFileNameShadow = SHADOW;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache(Batter.class.getSimpleName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

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
