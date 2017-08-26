/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.action.motion;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.action.pitchs.Fast;
import com.sf_lolitahag.action.pitchs.IPitch;
import com.sf_lolitahag.action.pitchs.Liner;
import com.sf_lolitahag.action.pitchs.Makyu;
import com.sf_lolitahag.action.pitchs.Straight;
import java.awt.Image;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * ボール実装
 */
public class Ball extends AbstractMotion {

  private static final int AXIS_X = 475;
  private static final int AXIS_Y = 245;
  private static final int PAINT_INTERVAL = 40;
  private static final int PAINT_INTERVAL_SPIN = 25;
  private static final int HIT_ZONE_START = 560;
  private static final int HIT_ZONE_END = 660;
  private static final List<String> BALL = Collections.singletonList("ball01");
  private static final List<String> BALL_SPIN = Arrays.asList("spin01", "spin02");
  private static final List<String> IMAGE_LIST = Arrays.asList("ball01", "spin01", "spin02");
  private int index = 0;
  private boolean isHitZone;
  private final Callback callback;
  private IPitch pitch;
  private final Timer spinTimer = new Timer(PAINT_INTERVAL_SPIN, (e) -> updateSpin());
  private final Timer paintTimer = new Timer(PAINT_INTERVAL, (e) -> updateBallView());

  public Ball(Callback callback) {
    this.callback = callback;

    cache = CACHE_MANAGER.createCache(Ball.class.getSimpleName(), CacheConfigurationBuilder
        .newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100))
        .build());
    IMAGE_LIST.forEach(name -> cache.put(name, Utils.getImageFromResources(getClass(), name)));

    init();
  }

  public void startPitch() {
    getPitchRand();
    isShow = true;
    paintTimer.start();
  }

  public boolean isHit() {
    if (isHitZone) {
      pitch = new Liner();
    }

    return isHitZone;
  }

  private void init() {
    axisX = AXIS_X;
    axisY = AXIS_Y;
    fileName = BALL.get(index);
    isShow = false;
    isHitZone = false;
  }

  private void getPitchRand() {
    switch (BallType.getBallType()) {
      case STRAIGHT:
        pitch = new Straight();
        break;
      case FAST:
        pitch = new Fast();
        break;
      case MAKYU:
        pitch = new Makyu();
        break;
    }
  }

  private void updateBallView() {
    int updateX = pitch.getUpdateX(axisX);
    axisX += updateX;
    int updateY = pitch.getUpdateY(axisY);
    axisY += updateY;
    isHitZone = axisY >= HIT_ZONE_START && axisY <= HIT_ZONE_END;

    if (pitch.isSpin()) {
      spinTimer.start();
    } else {
      spinTimer.stop();
      index = 0;
      fileName = BALL.get(index);
    }

    checkFinish(updateY);
  }

  private void checkFinish(int updateY) {
    int axisY = this.axisY + updateY;
    if (axisY < AXIS_Y || axisY >= 800) {
      paintTimer.stop();
      callback.onFinishPitch(axisY >= 800);
      init();
    }
  }

  private void updateSpin() {
    if (index == 0) {
      index = 1;
    } else if (index == 1) {
      index = 0;
    }
    fileName = BALL_SPIN.get(index);
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
