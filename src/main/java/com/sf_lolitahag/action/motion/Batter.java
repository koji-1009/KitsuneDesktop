/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.action.motion;

import com.sf_lolitahag.Utils;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * バッター実装
 */
public class Batter extends AbstractMotion {

  private static final int AXIS_X = 200;
  private static final int AXIS_Y = 475;
  private static final int SHADOW_X = 300;
  private static final int SHADOW_Y = 680;
  private static final int PAINT_INTERVAL = 100;
  private static final String SHADOW = "shadow01";
  private static final List<String> BATTER_MISS =
      Arrays.asList("batter01", "batter04", "batter03", "batter01");
  private static final List<String> BATTER_HIT =
      Arrays.asList("batter01", "batter02", "batter03", "batter01");
  private static final List<String> IMAGE_LIST =
      Arrays.asList("shadow01", "batter01", "batter02", "batter03", "batter04");
  private int index = 0;
  private boolean isHit;
  private final Timer timer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

  public Batter() {
    axisX = AXIS_X;
    axisY = AXIS_Y;
    axisShadowX = SHADOW_X;
    axisShadowY = SHADOW_Y;
    fileNameShadow = SHADOW;

    cache = CACHE_MANAGER.createCache(Batter.class.getSimpleName(), CacheConfigurationBuilder
        .newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100))
        .build());
    IMAGE_LIST.forEach(name -> cache.put(name, Utils.getImageFromResources(getClass(), name)));

    fileName = BATTER_HIT.get(index);
    isHit = false;
  }

  public void startSwing(boolean isHit) {
    this.isHit = isHit;
    timer.start();
  }

  private void updateFileName() {
    index++;
    fileName = isHit ? BATTER_HIT.get(index) : BATTER_MISS.get(index);
    if (index >= 3) {
      timer.stop();
      index = 0;
      isHit = false;
    }
  }
}
