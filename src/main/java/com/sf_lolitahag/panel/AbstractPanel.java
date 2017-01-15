package com.sf_lolitahag.panel;

import javax.swing.JPanel;

/**
 * 表示するパネルのBaseクラス SpaceKeyを押した場合の処理を継承クラスで実装する
 */
public abstract class AbstractPanel extends JPanel {

  public abstract void onSpaceKeyPress();
}
