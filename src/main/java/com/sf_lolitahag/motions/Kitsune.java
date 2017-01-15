package com.sf_lolitahag.motions;

import com.sf_lolitahag.Utils;
import java.awt.Image;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

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
  private static final String[] KITSUNE_WIN = {
      "nojanoja01", "nojanoja02", "nojanoja01", "nojanoja02"
  };
  private static final String[] IMAGE_LIST = {
      "shadow01",
      "stay01",
      "stay02",
      "throw01",
      "throw02",
      "nojanoja01",
      "nojanoja02",
      "mae_ga_mienee"
  };
  private int index = 0;
  private MODE mode = MODE.THROW;
  private Callback callback;
  private Timer timer = new Timer(PAINT_INTERVAL, (e) -> updateConfig());

  public Kitsune(Callback callback) {
    axisX = AXIS_X;
    axisY = AXIS_Y;
    axisShadowX = SHADOW_X;
    axisShadowY = SHADOW_Y;
    fileNameShadow = SHADOW;

    Class tmpClass = getClass();
    cache =
        CACHE_MANAGER.createCache(
            Kitsune.class.getSimpleName(),
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, Image.class, ResourcePoolsBuilder.heap(100))
                .build());
    for (String fileName : IMAGE_LIST) {
      cache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
    }

    this.callback = callback;
    updateFileName();
    startThrow();
  }

  public void showWinOrLosePose(boolean isWin) {
    if (isWin) {
      mode = MODE.WIN;
    } else {
      mode = MODE.LOSE;
    }
    updateFileName();
    timer.start();
  }

  private void checkAnimationFinish() {
    switch (mode) {
      case THROW:
        if (index >= THROW_ANIMATION.length) {
          timer.stop();
          index = 0;
          callback.onFinishThrow();
        }
        break;
      case WIN:
        if (index >= WIN_ANIMATION.length) {
          timer.stop();
          index = 0;
          startThrow();
        }
        break;
      case LOSE:
        if (index >= LOSE_ANIMATION.length) {
          timer.stop();
          index = 0;
          startThrow();
        }
        break;
    }
  }

  private void startThrow() {
    mode = MODE.THROW;
    timer.start();
  }

  private void updateConfig() {
    updateFileName();
    index++;
    checkAnimationFinish();
  }

  private void updateFileName() {
    switch (mode) {
      case THROW:
        fileName = KITSUNE_THROW[THROW_ANIMATION[index]];
        break;
      case WIN:
        fileName = KITSUNE_WIN[WIN_ANIMATION[index]];
        break;
      case LOSE:
        fileName = KITSUNE_LOSE[LOSE_ANIMATION[index]];
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
