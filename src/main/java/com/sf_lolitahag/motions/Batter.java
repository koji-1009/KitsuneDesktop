package com.sf_lolitahag.motions;

import com.sf_lolitahag.Utils;
import java.awt.Image;
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
  private static final String[] BATTER_MISS = {"batter01", "batter04", "batter03", "batter01"};
  private static final String[] BATTER_HIT = {"batter01", "batter02", "batter03", "batter01"};
  private static final String[] IMAGE_LIST = {"shadow01", "batter01", "batter02", "batter03",
      "batter04"};
  private int index = 0;
  private boolean isHit;
  private Timer timer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

  public Batter() {
    axisX = AXIS_X;
    axisY = AXIS_Y;
    axisShadowX = SHADOW_X;
    axisShadowY = SHADOW_Y;
    fileNameShadow = SHADOW;

    Class tmpClass = getClass();
    cache =
        CACHE_MANAGER.createCache(
            Batter.class.getSimpleName(),
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, Image.class, ResourcePoolsBuilder.heap(100))
                .build());
    for (String fileName : IMAGE_LIST) {
      cache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
    }

    fileName = BATTER_HIT[index];
    isHit = false;
  }

  public void startSwing(boolean isHit) {
    this.isHit = isHit;
    timer.start();
  }

  private void updateFileName() {
    index++;
    fileName = isHit ? BATTER_HIT[index] : BATTER_MISS[index];
    if (index >= 3) {
      timer.stop();
      index = 0;
      isHit = false;
    }
  }
}
