package com.github.brandonxue.art_gallery_manager;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.plaf.InsetsUIResource;

@SuppressWarnings("serial")
class GridBagCBuilder extends GridBagConstraints {
    public GridBagCBuilder() {}

    /**
     * Provides a variable args implementation for instantiating GridBagConstraints.
     * Inset integers are provided directly, rather than an insets object.
     * All arguments should be provided as doubles, but will be cast as the following:
     *  
     * int gridx, int gridy, int gridwidth, int gridheight,
     * double weightx, double weighty,
     * int anchor, int fill,
     * int insets top, int insets right, int insets bottom, int insets left,
     * int ipadx, int ipady
     * 
     * @param params The parameters for the non-default GridBagConstraints constructor as doubles. The order adheres to original GridBagConstraints parameter ordering, with Insets set as inline doubles rather than as an object.
     */
    public GridBagCBuilder(double... params) {
        int length = params.length;
        int top = 0, right = 0, bottom = 0, left = 0; // Initialize default values for insets
        switch (length) {
            case 14:                
                ipady = (int)params[13];
            case 13:
                ipadx = (int)params[12];
            case 12:
                left = (int)params[11];
            case 11:
                bottom = (int)params[10];
            case 10:
                right = (int)params[9];
            case 9:
                top = (int)params[8];
                insets = new Insets(top, right, bottom, left);
            case 8:
                fill = (int)params[7];
            case 7:
                anchor = (int)params[6];
            case 6:
                weighty = params[5];
            case 5:
                weightx = params[4];
            case 4:
                gridheight = (int)params[3];
            case 3:
                gridwidth = (int)params[2];
            case 2:
                gridy = (int)params[1];
            case 1:
                gridx = (int)params[0];
            default:
        }
    }

    // Setter methods for use with method cascading
    public GridBagCBuilder sGridx(int gx) {gridx = gx; return this;}
    public GridBagCBuilder sGridy(int gy) {gridy = gy; return this;}
    public GridBagCBuilder sGridwidth(int gwidth) {gridwidth = gwidth; return this;}
    public GridBagCBuilder sGridheight(int gheight) {gridheight = gheight; return this;}
    public GridBagCBuilder sGeightx(double wx) {weightx = wx; return this;}
    public GridBagCBuilder sGeighty(int wy) {weighty = wy; return this;}
    public GridBagCBuilder sAnchor(int a) {anchor = a; return this;}
    public GridBagCBuilder sFill(int f) {fill = f; return this;}
    public GridBagCBuilder sInsets(Insets ins) {insets = ins; return this;}
    public GridBagCBuilder sInsets(int top, int right, int bottom, int left) {
        insets = new Insets(top, right, bottom, left);
        return this;
    }
    public GridBagCBuilder sIpadx(int ipx) {ipadx = ipx; return this;}
    public GridBagCBuilder sIpady(int ipy) {ipady = ipy; return this;}
}