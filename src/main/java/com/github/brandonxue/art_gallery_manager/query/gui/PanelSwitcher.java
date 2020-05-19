package com.github.brandonxue.art_gallery_manager.query;

import javax.swing.JTabbedPane;

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