/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.action.motion;

import java.awt.Image;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

/**
 * パネル上で表示するオブジェクトのBaseクラス 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
public abstract class AbstractMotion implements IMotion {

  private static final String EMPTY = "";
  protected static final CacheManager CACHE_MANAGER =
      CacheManagerBuilder.newCacheManagerBuilder().build(true);
  protected int axisX = 0;
  protected int axisY = 0;
  protected int axisShadowX = 0;
  protected int axisShadowY = 0;
  protected boolean isShow = true;
  protected String fileName = EMPTY;
  protected String fileNameShadow = EMPTY;
  protected Cache<String, Image> cache;

  @Override
  public final int getAxisX() {
    return axisX;
  }

  @Override
  public final int getAxisY() {
    return axisY;
  }

  @Override
  public final int getAxisShadowX() {
    return axisShadowX;
  }

  @Override
  public final int getAxisShadowY() {
    return axisShadowY;
  }

  @Override
  public final Image getBodyImage() {
    if (fileName.equals(EMPTY)) {
      return null;
    }
    return cache.get(fileName);
  }

  @Override
  public final Image getShadowImage() {
    if (fileNameShadow.equals(EMPTY)) {
      return null;
    }
    return cache.get(fileNameShadow);
  }

  @Override
  public final boolean isShow() {
    return isShow;
  }
}
