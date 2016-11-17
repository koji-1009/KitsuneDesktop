package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.motions.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends BasePanel {

    private static final String BACK = "back";
    private static final int PAINT_INTERVAL = 30;
    private final ArrayList<BaseMotion> mMotionList = new ArrayList<>();
    private Kitsune mKitsune;
    private Batter mBatter;
    private Ball mBall;

    public GamePanel() {
        initMotions();
        startRepaintTimer();
    }

    private void initMotions() {
        mKitsune = new Kitsune(() -> mBall.startPitch());
        mBatter = new Batter();
        mBall = new Ball((isWin) -> mKitsune.showResult(isWin));
        mMotionList.add(mKitsune);
        mMotionList.add(mBatter);
        mMotionList.add(mBall);
    }

    private void startRepaintTimer() {
        new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
    }

    @Override
    public void onSpaceTap() {
        boolean isHit = BallState.getInstance().isHit();
        mBatter.startSwing(isHit);
        mBall.isHit(isHit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Class tmpClass = getClass();
        g.drawImage(Utils.getImageFromRes(tmpClass, BACK), 0, 0, null);
        mMotionList.forEach(motion -> {
            if (motion.isShow()) {
                g.drawImage(Utils.getImageFromRes(tmpClass, motion.getFileNameShadow()), motion.getAxisShadowX(), motion.getAxisShadowY(), null);
                g.drawImage(Utils.getImageFromRes(tmpClass, motion.getFileName()), motion.getAxisX(), motion.getAxisY(), null);
            }
        });
    }
}
