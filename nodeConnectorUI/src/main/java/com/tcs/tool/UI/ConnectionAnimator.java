/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.tcs.tools.resources.ResourceLocator;

public class ConnectionAnimator implements Runnable {
    private final AtomicBoolean animate = new AtomicBoolean(false);
    private boolean on = false;
    private JLabel comp;
    private String onImg, offImg;

    /**
     * @param comp
     * @param onImg
     * @param offImg
     */
    public ConnectionAnimator(JLabel comp, String onImg, String offImg) {
        super();
        this.comp = comp;
        this.onImg = onImg;
        this.offImg = offImg;
    }

    public static ConnectionAnimator startAnimation(JLabel comp, String onImage, String offImage) {
        ConnectionAnimator animator = new ConnectionAnimator(comp, onImage, offImage);
        animator.setAnimate(true);
        Thread t = new Thread(animator);
        t.start();
        return animator;
    }

    public void stopAnimation(){
        this.setAnimate(false);
    }
    @Override
    public void run() {
        if (offImg != null && onImg != null) {
            ImageIcon waitPng = ResourceLocator.getImageIcon(offImg);
            ImageIcon offPng = ResourceLocator.getImageIcon(onImg);
            while (animate.get()) {
                System.out.println("Animation continues...:on:"+on);
                if (on) {
                    comp.setIcon(waitPng);
                } else {
                    comp.setIcon(offPng);
                }
                on = !on;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean getAnimate() {
        return animate.get();
    }

    public void setAnimate(boolean animate) {
        this.animate.set(animate);
    }
}