package com.sf_lolitahag.motions;

import java.awt.Image;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

/**
 * パネル上で表示するオブジェクトのBaseクラス 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
public abstract class AbstractMotion {

  protected static final CacheManager CACHE_MANAGER =
      CacheManagerBuilder.newCacheManagerBuilder().build(true);
  private static final String EMPTY = "";
  protected int axisX = 0;
  protected int axisY = 0;
  protected int axisShadowX = 0;
  protected int axisShadowY = 0;
  protected boolean isShow = true;
  protected String fileName = EMPTY;
  protected String fileNameShadow = EMPTY;
  protected Cache<String, Image> cache;

  public final int getAxisX() {
    return axisX;
  }

  public final int getAxisY() {
    return axisY;
  }

  public final int getAxisShadowX() {
    return axisShadowX;
  }

  public final int getAxisShadowY() {
    return axisShadowY;
  }

  public final Image getBodyImage() {
    if (fileName.equals(EMPTY)) {
      return null;
    }
    return cache.get(fileName);
  }

  public final Image getShadowImage() {
    if (fileNameShadow.equals(EMPTY)) {
      return null;
    }
    return cache.get(fileNameShadow);
  }

  public final boolean isShow() {
    return isShow;
  }
}
