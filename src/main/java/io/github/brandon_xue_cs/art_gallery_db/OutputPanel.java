package io.github.brandon_xue_cs.art_gallery_db;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;

import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("serial")
public class OutputPanel extends JScrollPane {
    private JTable tableView;

    OutputPanelServicer servicer;

    public OutputPanel() {
        setPreferredSize(new DimensionUIResource(700, 400));

        tableView = new JTable();
        tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        setViewportView(tableView);
    }

    public void setServicer(OutputPanelServicer s) {
        servicer = s;
        s.addModelUpdateListener(msgs -> {
            tableView.setModel((DefaultTableModel)msgs[0]);
        });
    }
    
}