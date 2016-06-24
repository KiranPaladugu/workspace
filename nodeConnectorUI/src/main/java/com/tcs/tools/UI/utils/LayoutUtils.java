package com.tcs.tools.UI.utils;

import java.awt.*;

import javax.swing.*;

import com.tcs.tool.UI.ButtonControlPanel;
import com.tcs.tool.UI.ControlPanel;

public class LayoutUtils {

    public static GridBagConstraints getDefaultGridBagConstraints(final int x, final int y) {
        final GridBagConstraints gc = new GridBagConstraints();
        // gc.anchor = GridBagConstraints.PAGE_START;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridheight = 1;
        gc.gridwidth = 1;
        gc.gridx = x;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridy = y;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(1, 1, 1, 1);
        return gc;
    }

    public static GridBagConstraints getDefaultGridBagConstraints(final int x, final int y, final int fillMethod) {
        final GridBagConstraints gc = getDefaultGridBagConstraints(x, y);
        gc.fill = fillMethod;
        return gc;
    }

    public static GridBagConstraints getDefaultGridBagConstraints(final int x, final int y, final double weightX,
            final double weightY) {
        final GridBagConstraints gc = getDefaultGridBagConstraints(x, y);
        gc.weightx = weightX;
        gc.weighty = weightY;
        return gc;
    }

    public static LayoutUtils getUtils(final String name) {
        return new LayoutUtils(name);
    }

    private LayoutUtils(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private String name = "";

    private int fill = GridBagConstraints.BOTH;
    private int gridx = -1;
    private int gridy = -1;
    private int hight = 0;
    private int width = 0;
    private int anchor = GridBagConstraints.CENTER;
    private Insets insets = new Insets(1, 1, 1, 1);
    private double weightx = 1;
    private double weighty = 1;

    /**
     * @return the gridx
     */
    public int getGridx() {
        return gridx;
    }

    /**
     * @param gridx
     *            the gridx to set
     */
    public void setGridx(final int gridx) {
        this.gridx = gridx;
    }

    /**
     * @return the gridy
     */
    public int getGridy() {
        return gridy;
    }

    /**
     * @param gridy
     *            the gridy to set
     */
    public void setGridy(final int gridy) {
        this.gridy = gridy;
    }

    /**
     * @return the weightx
     */
    public double getWeightx() {
        return weightx;
    }

    /**
     * @param weightx
     *            the weightx to set
     */
    public void setWeightx(final double weightx) {
        this.weightx = weightx;
    }

    /**
     * @return the weighty
     */
    public double getWeighty() {
        return weighty;
    }

    /**
     * @param weighty
     *            the weighty to set
     */
    public void setWeighty(final double weighty) {
        this.weighty = weighty;
    }

    public void setFillType(final int fill) {
        this.fill = fill;
    }

    public void setAnchor(final int anchor) {
        this.anchor = anchor;
    }

    private GridBagConstraints getDefaultConstaints(final int width, final int height) {
        final GridBagConstraints constrints = new GridBagConstraints();
        update();
        constrints.fill = fill;
        constrints.gridx = gridx;
        constrints.gridy = gridy;
        constrints.gridheight = width;
        constrints.gridwidth = height;
        constrints.anchor = anchor;
        constrints.insets = insets;
        return constrints;
    }

    public GridBagConstraints getNextColumnConstaints() {
        return getNextColumnConstaints(1, 1);
    }

    public GridBagConstraints getNextColumnConstaints(final int width, final int height) {
        final GridBagConstraints constrints = getDefaultConstaints(width, height);
        update();
        constrints.gridx = this.width;
        this.width = this.width + width;
        return constrints;
    }

    public GridBagConstraints getNextRowConstaints() {
        return getNextRowConstaints(1, 1);
    }

    private void update() {
        if (gridx < 0) {
            gridx = 0;
        }

        if (gridy < 0) {
            gridy = 0;
        }
    }

    public GridBagConstraints getNextRowConstaints(final int width, final int height) {
        final GridBagConstraints constrints = getDefaultConstaints(width, height);
        constrints.gridy = this.hight;
        this.hight = this.hight + height;
        this.width = 0;
        return constrints;
    }

    public static ControlPanel arrangeComponantsInColoumn(final String title, final boolean expand,
            final Component... components) {
        final ControlPanel panel = arrangeComponantsInColoumn(null, expand, GridBagConstraints.BOTH, components);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    public static ControlPanel arrangeComponantsInColoumn(final String title, int fillType, final boolean expand,
            final Component... components) {
        final ControlPanel panel = arrangeComponantsInColoumn(null, expand, fillType, components);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    public static ControlPanel arrangeComponantsInColoumn(final String title, final Component... components) {
        return arrangeComponantsInColoumn(title, true, components);
    }

    public static ControlPanel arrangeComponantsInColoumn(final Component... components) {
        return arrangeComponantsInColoumn(null, true, GridBagConstraints.BOTH, components);
    }

    public static ControlPanel arrangeComponantsInColoumn(final boolean expand, final Component... components) {
        return arrangeComponantsInColoumn(null, expand, GridBagConstraints.BOTH, components);
    }

    public static ControlPanel arrangeComponantsInColoumn(final boolean expand, final int fillType,
            final Component... components) {
        return arrangeComponantsInColoumn(null, expand, fillType, components);
    }

    public static ControlPanel arrangeComponantsInColoumn(final ControlPanel panel, final boolean expand, final int fillType,
            final Component... components) {
        ControlPanel panelToreturn = panel;
        if (panelToreturn == null) {
            panelToreturn = new ControlPanel();
        }
        panelToreturn.setLayout(new GridBagLayout());
        final LayoutUtils utils = getUtils(null);
        if (components != null) {
            for (final Component comp : components) {
                GridBagConstraints gc = utils.getNextColumnConstaints();
                gc.fill = fillType;
                gc = getCaluculatedGC(gc, expand, utils, comp,true);
                checkExpansionType(comp, gc);

                panelToreturn.add(comp, gc);
            }
        }
        return panelToreturn;
    }

    public static ControlPanel arrangeComponantsInRow(final String panelTitle, final Component... components) {
        return arrangeComponantsInRow(panelTitle, true, components);
    }

    public static ControlPanel arrangeComponantsInRow(final String panelTitle, final boolean expand,
            final Component... components) {
        final ControlPanel panel = arrangeComponantsInRow(null, expand, GridBagConstraints.BOTH, components);
        panel.setBorder(BorderFactory.createTitledBorder(panelTitle));
        return panel;
    }

    public static ControlPanel arrangeComponantsInRow(final String panelTitle, final int fillType, final boolean expand,
            final Component... components) {
        final ControlPanel panel = arrangeComponantsInRow(null, expand, fillType, components);
        panel.setBorder(BorderFactory.createTitledBorder(panelTitle));
        return panel;

    }

    public static ControlPanel arrangeComponantsInRow(final boolean expand, final int fillType, final Component... components) {
        return arrangeComponantsInRow(null, expand, fillType, components);

    }

    public static ControlPanel arrangeComponantsInRow(final Component... components) {
        return arrangeComponantsInRow(null, true, GridBagConstraints.BOTH, components);
    }

    public static ControlPanel arrangeComponantsInRow(final boolean expand, final Component... components) {
        return arrangeComponantsInRow(null, expand, GridBagConstraints.BOTH, components);
    }

    public static ControlPanel arrangeComponantsInRow(final ControlPanel panel, final boolean expand, final int fillType,
            final Component... components) {
        ControlPanel panelToreturn = panel;
        if (panelToreturn == null) {
            panelToreturn = new ControlPanel();
        }
        panelToreturn.setLayout(new GridBagLayout());
        final LayoutUtils utils = getUtils(null);
        if (components != null) {
            for (final Component comp : components) {
                GridBagConstraints gc = utils.getNextRowConstaints();
                gc.fill = fillType;
                gc = getCaluculatedGC(gc, expand, utils, comp,false);
                checkExpansionType(comp, gc);
                panelToreturn.add(comp, gc);
            }
        }
        return panelToreturn;
    }

    /**
     * @param expand
     * @param utils
     * @param comp
     * @return
     */
    private static GridBagConstraints getCaluculatedGC(GridBagConstraints gc, final boolean expand, final LayoutUtils utils,
            final Component comp,boolean horizontal) {
        if (comp instanceof JSeparator) {
            if(!horizontal){
            gc.weightx = 1;
            gc.weighty = 0;
            gc.fill = GridBagConstraints.HORIZONTAL;
            }else{
                gc.weightx = 0;
                gc.weighty = 1;
                gc.fill = GridBagConstraints.VERTICAL;
               ((JSeparator) comp).setOrientation(SwingConstants.VERTICAL);
               
            }
        } else if (!(comp instanceof JLabel) && !(comp instanceof JButton) && !(comp instanceof ButtonControlPanel) &&!(comp instanceof JCheckBox) && expand) {
            gc.weightx = 1;
            gc.weighty = 1;
        } else {
            gc.weightx = 0;
            gc.weighty = 0;
        }
        return gc;
    }

    /**
     * @param comp
     * @param gc
     */
    private static void checkExpansionType(final Component comp, final GridBagConstraints gc) {
        if (comp instanceof ControlPanel) {
            ControlPanel cpanel = (ControlPanel) comp;
            if (cpanel.isExpandable()) {
                int key = cpanel.getExpandPolicy();
                //				System.out.println("expandPlcy" + key);
                switch (key) {
                case ControlPanel.HORIZONTAL_FULL:
                    gc.weightx = 1;
                    gc.weighty = 0;
                    gc.fill = GridBagConstraints.HORIZONTAL;
                    break;
                case ControlPanel.HORIZONTAL_HALF:
                    gc.weightx = 0.5;
                    gc.weighty = 0;
                    gc.fill = GridBagConstraints.HORIZONTAL;
                    break;
                case ControlPanel.VERTICAL_FULL:
                    gc.weightx = 0;
                    gc.weighty = 1;
                    gc.fill = GridBagConstraints.VERTICAL;
                    break;
                case ControlPanel.VERTICAL_HALF:
                    gc.weightx = 0;
                    gc.weighty = 0.5;
                    gc.fill = GridBagConstraints.VERTICAL;
                    break;
                case ControlPanel.BOTH:
                    gc.weightx = 1;
                    gc.weighty = 1;
                    gc.fill = GridBagConstraints.BOTH;
                    break;
                case ControlPanel.HALF:
                    gc.weightx = 0.5;
                    gc.weighty = 0.5;
                    gc.fill = GridBagConstraints.BOTH;
                    break;
                case ControlPanel.QAURTER:
                    gc.weightx = 0.25;
                    gc.weighty = 0.25;
                    gc.fill = GridBagConstraints.BOTH;
                case ControlPanel.NONE:
                    gc.weightx = 0;
                    gc.weighty = 0;
                    gc.fill = GridBagConstraints.NONE;
                default:
                    if (key > 1000 && key < 1100) {
                        int val = key % 1000;
                        double newVal = ((double) val) / (double) 100;
                        //						System.out.println("percent"+newVal);
                        gc.weightx = newVal;
                        gc.weighty = newVal;
                        gc.fill = GridBagConstraints.BOTH;
                    } else {
                        gc.weightx = 0;
                        gc.weighty = 0;
                        gc.fill = GridBagConstraints.NONE;
                    }
                    break;
                }
            } else {
                gc.weightx = 0;
                gc.weighty = 0;
                gc.fill = GridBagConstraints.NONE;
            }
            //			System.out.println("GCFILL" + gc.fill);
        }
    }

}
