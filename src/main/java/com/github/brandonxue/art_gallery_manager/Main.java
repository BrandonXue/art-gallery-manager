package com.github.brandonxue.art_gallery_manager;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.github.brandonxue.art_gallery_manager.query.*;

@SuppressWarnings("serial")
public class Main {
    public static void main(String args[]) {
        // Register the driver with DriverManager
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println("Likely encountered a driver issue:");
            System.out.println(e.toString());
            return;
        }

        Runnable r = () -> { // Swing components are not thread safe and must be run on the Event Dispatch Thread
            JFrame frame = new JFrame("Art Gallery Manager");
            frame.add(QueryPanelBuilder.buildQueryPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            //System.out.println(frame.getSize().toString());
        };
        EventQueue.invokeLater(r);
    }
}