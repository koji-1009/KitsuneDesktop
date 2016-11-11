package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.motions.Ball;
import com.sf_lolitahag.motions.BaseMotion;
import com.sf_lolitahag.motions.Batter;
import com.sf_lolitahag.motions.Kitsune;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends BasePanel {

    private static final String BACK_PNG = "back";
    private static final int PAINT_INTERVAL = 100;
    private static final String SHADOW_PNG = "shadow01";
    private final ArrayList<BaseMotion> mMotionList = new ArrayList<>();
    private Kitsune mKitsune;
    private Batter mBatter;
    private Ball mBall;

    public GamePanel() {
        initMotions();
        startRepaintTimer();
    }

    private void initMotions() {
        mKitsune = new Kitsune();
        mBatter = new Batter();
        mMotionList.add(mKitsune);
        mMotionList.add(mBatter);
        mBall = new Ball();
    }

    private void startRepaintTimer() {
        ActionListener taskPerformer = (e) -> repaint();
        new Timer(PAINT_INTERVAL, taskPerformer).start();
    }

    @Override
    public void onSpaceTap() {
        mBatter.startSwing(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Class tmpClass = getClass();
        g.drawImage(Utils.getImageFromRes(tmpClass, BACK_PNG), 0, 0, null);
        g.drawImage(Utils.getImageFromRes(tmpClass, SHADOW_PNG), mKitsune.getShadowX(), mKitsune.getShadowY(), null);
        g.drawImage(Utils.getImageFromRes(tmpClass, SHADOW_PNG), mBatter.getShadowX(), mBatter.getShadowY(), null);
        mMotionList.forEach(motion -> g.drawImage(Utils.getImageFromRes(tmpClass, motion.getFileName()), motion.getAxisX(), motion.getAxisY(), null));
        if (mBall.isShow()) {
            g.drawImage(Utils.getImageFromRes(tmpClass, mBall.getFileName()), mBall.getAxisX(), mBall.getAxisY(), null);
        }
    }
}
