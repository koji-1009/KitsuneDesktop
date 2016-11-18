package com.sf_lolitahag.motions;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

import java.awt.*;

/**
 * パネル上で表示するオブジェクトのBaseクラス
 * 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
public abstract class AbstractMotion {

    private static final String EMPTY = "";
    protected static final CacheManager CACHE_MANAGER = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    protected int mAxisX = 0;
    protected int mAxisY = 0;
    protected int mAxisShadowX = 0;
    protected int mAxisShadowY = 0;
    protected String mFileName = EMPTY;
    protected String mFileNameShadow = EMPTY;
    protected boolean mIsShow = true;
    protected Cache<String, Image> mCache;

    public final int getAxisX() {
        return mAxisX;
    }

    public final int getAxisY() {
        return mAxisY;
    }

    public final int getAxisShadowX() {
        return mAxisShadowX;
    }

    public final int getAxisShadowY() {
        return mAxisShadowY;
    }

    public final Image getFileName() {
        if (mFileName.equals(EMPTY)) {
            return null;
        }
        return mCache.get(mFileName);
    }

    public final Image getFileNameShadow() {
        if (mFileNameShadow.equals(EMPTY)) {
            return null;
        }
        return mCache.get(mFileNameShadow);
    }

    public final boolean isShow() {
        return mIsShow;
    }
}
