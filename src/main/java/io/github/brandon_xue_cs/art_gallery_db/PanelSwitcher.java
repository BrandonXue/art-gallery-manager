package io.github.brandon_xue_cs.art_gallery_db;

import javax.swing.JTabbedPane;

import io.github.brandon_xue_cs.art_gallery_db.OutputPanelServicer;

@SuppressWarnings("serial")
public class PanelSwitcher extends JTabbedPane {
    OutputPanelServicer outputPanelServicer;

    public PanelSwitcher() {}

    /**
     * This method must be called after at least one JPanel has been added.
     * @param s the OutputPanelServicer that manages various table models
     */
    public void setOutputPanelServicer(OutputPanelServicer s) {
        outputPanelServicer = s;
        addChangeListener(l -> {
            outputPanelServicer.paneChanged(this.getSelectedIndex());
        });
    }

}