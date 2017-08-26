/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.action.motion.IMotion;
import com.sf_lolitahag.action.motion.Ball;
import com.sf_lolitahag.action.motion.Batter;
import com.sf_lolitahag.action.motion.Kitsune;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends AbstractPanel {

  private static final String BACK = "back";
  private static final int PAINT_INTERVAL = 30;
  private final ArrayList<IMotion> motions = new ArrayList<>();
  private final Image image;
  private Kitsune kitsune;
  private Batter batter;
  private Ball ball;

  public GamePanel() {
    image = Utils.getImageFromResources(getClass(), BACK);
    initMotions();
    startRepaintTimer();
  }

  @Override
  public void onSpaceKeyPress() {
    batter.startSwing(ball.isHit());
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(image, 0, 0, null);
    motions.forEach(
        motion -> {
          if (motion.isShow()) {
            g.drawImage(
                motion.getShadowImage(), motion.getAxisShadowX(), motion.getAxisShadowY(), null);
            g.drawImage(motion.getBodyImage(), motion.getAxisX(), motion.getAxisY(), null);
          }
        });
  }

  private void initMotions() {
    kitsune = new Kitsune(() -> ball.startPitch());
    batter = new Batter();
    ball = new Ball((isWin) -> kitsune.showWinOrLosePose(isWin));
    motions.add(kitsune);
    motions.add(batter);
    motions.add(ball);
  }

  private void startRepaintTimer() {
    new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
  }
}
