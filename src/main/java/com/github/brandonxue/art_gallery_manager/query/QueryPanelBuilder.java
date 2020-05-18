package com.github.brandonxue.art_gallery_manager;

import java.awt.Container;

import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class QueryPanelBuilder {
    private static final int NUM_PANELS = 2;

    public QueryPanelBuilder(){}

    public static JPanel buildQueryPanel() {
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));

        PanelSwitcher tabbedPane = new PanelSwitcher();
        FormPanel formPanel = new FormPanel();
        CustomPanel customPanel = new CustomPanel();
        OutputPanel outputPanel = new OutputPanel();

        FormPanelServicer formPanelServicer = new FormPanelServicer();
        formPanel.setServicer(formPanelServicer);
        CustomPanelServicer customPanelServicer = new CustomPanelServicer();
        customPanel.setServicer(customPanelServicer);
        OutputPanelServicer outputPanelServicer = new OutputPanelServicer(2);
        outputPanel.setServicer(outputPanelServicer);

        formPanelServicer.addOutputPanelServicer(outputPanelServicer);
        customPanelServicer.addOutputPanelServicer(outputPanelServicer);

        tabbedPane.addTab("Form", formPanel);
        tabbedPane.addTab("Custom", customPanel);
        tabbedPane.setOutputPanelServicer(outputPanelServicer);

        queryPanel.add(tabbedPane);
        queryPanel.add(outputPanel);

        //OutputPanelServicer = new OutputPanelServicer(NUM_PANELS);

        return queryPanel;
    }
}