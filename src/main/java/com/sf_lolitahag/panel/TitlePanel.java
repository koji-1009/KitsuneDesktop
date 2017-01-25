/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import java.awt.Graphics;

public class TitlePanel extends AbstractPanel {

  private static final String TITLE = "title";
  private Callback callback;

  public TitlePanel(Callback callback) {
    this.callback = callback;
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(Utils.getImageFromResources(getClass(), TITLE), 0, 0, null);
  }

  @Override
  public void onSpaceKeyPress() {
    callback.gotoGamePanel();
  }

  public interface Callback {

    void gotoGamePanel();
  }
}
