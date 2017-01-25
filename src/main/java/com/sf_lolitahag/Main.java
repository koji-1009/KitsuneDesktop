/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag;

import com.sf_lolitahag.panel.AbstractPanel;
import com.sf_lolitahag.panel.GamePanel;
import com.sf_lolitahag.panel.TitlePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {

  private static final int FRAME_WIDTH = 1000;
  private static final int FRAME_HEIGHT = 800;
  private static final String FRAME_TITLE = "ロリのバーさんのホームランダービー";
  private static JFrame frame;
  private static AbstractPanel panel;

  public static void main(String args[]) {
    SwingUtilities.invokeLater(Main::initFrame);
  }

  private static void initFrame() {
    frame = new JFrame(FRAME_TITLE);
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setLocationRelativeTo(null); //初期画面表示 位置を中央に
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //CLOSEでプログラム終了
    frame.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            // nop
          }

          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
              panel.onSpaceKeyPress();
            }
          }

          @Override
          public void keyReleased(KeyEvent e) {
            // nop
          }
        });

    panel = new TitlePanel(Main::changePanel);
    frame.add(panel);
    frame.setVisible(true);
  }

  private static void changePanel() {
    frame.remove(panel);
    panel = new GamePanel();
    frame.add(panel);
    frame.setVisible(true);
  }
}
