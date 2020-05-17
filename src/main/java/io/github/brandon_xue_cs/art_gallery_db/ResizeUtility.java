package io.github.brandon_xue_cs.art_gallery_db;

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