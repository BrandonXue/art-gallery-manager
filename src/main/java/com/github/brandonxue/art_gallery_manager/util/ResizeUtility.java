package com.github.brandonxue.art_gallery_manager;

import javax.swing.JComponent;

import javax.swing.plaf.DimensionUIResource;

@SuppressWarnings("serial")
public class ResizeUtility {
    public static void setAbsoluteSize(JComponent component, int width, int height) {
        DimensionUIResource d = new DimensionUIResource(width, height);
        component.setPreferredSize(d);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
    }
}