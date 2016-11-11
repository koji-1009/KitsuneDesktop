package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;

import java.awt.*;

public class TitlePanel extends BasePanel {

    private static final String TITLE_PNG = "title";
    private Callback mCallback;

    public TitlePanel(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(Utils.getImageFromRes(getClass(), TITLE_PNG), 0, 0, null);
    }

    @Override
    public void onSpaceTap() {
        mCallback.gotoGamePanel();
    }

    public interface Callback {
        void gotoGamePanel();
    }
}
