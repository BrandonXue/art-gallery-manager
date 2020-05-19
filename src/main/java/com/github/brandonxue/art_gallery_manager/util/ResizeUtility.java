package com.github.brandonxue.art_gallery_manager.util;

import javax.swing.JComponent;

import javax.swing.plaf.DimensionUIResource;

@SuppressWarnings("serial")
public class ResizeUtility {

    /**
     * Sets the preferred, minimum, and maximum dimensions of a JComponent.
     * @param component the JComponent to be adjusted.
     * @param width the desired width.
     * @param height the desired height.
     */
    public static void setAbsoluteSize(JComponent component, int width, int height) {
        DimensionUIResource d = new DimensionUIResource(width, height);
        component.setPreferredSize(d);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
    }
}