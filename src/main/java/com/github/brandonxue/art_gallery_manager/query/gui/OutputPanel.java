package com.github.brandonxue.art_gallery_manager.query;

import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class OutputPanel extends JScrollPane {
    private JTable tableView;

    public OutputPanel() {
        setPreferredSize(new DimensionUIResource(700, 400));

        tableView = new JTable();
        tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setViewportView(tableView);
    }

    /**
     * Link this JScrollPane with its logical counterpart so that it can be pushed with view updates.
     * @param s the OutputPanelServicer that will function as this Pane's logical component
     */
    public void setServicer(OutputPanelServicer s) {
        // Use a listener so check for updates to the view
        s.addModelUpdateListener(msgs -> {
            tableView.setModel((DefaultTableModel)msgs[0]);
        });

        // Send a reference of the JTable so the servicer can manipulate its size
        s.setTableReference(tableView);
    } 
}