package com.sf_lolitahag;

import com.sf_lolitahag.panel.AbstractPanel;
import com.sf_lolitahag.panel.GamePanel;
import com.sf_lolitahag.panel.TitlePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 800;
    private static final String FRAME_TITLE = "ロリのバーさんのホームランダービー";
    private static JFrame mFrame;
    private static AbstractPanel mPanel;

    public static void main(String args[]) {
        SwingUtilities.invokeLater(Main::initFrame);
    }

    private static void initFrame() {
        mFrame = new JFrame(FRAME_TITLE);
        mFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mFrame.setLocationRelativeTo(null); //初期画面表示 位置を中央に
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //CLOSEでプログラム終了
        mFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // nop
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    mPanel.onSpaceKeyPress();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // nop
            }
        });

        mPanel = new TitlePanel(Main::changePanel);
        mFrame.add(mPanel);
        mFrame.setVisible(true);
    }

    private static void changePanel() {
        mFrame.remove(mPanel);
        mPanel = new GamePanel();
        mFrame.add(mPanel);
        mFrame.setVisible(true);
    }
}
