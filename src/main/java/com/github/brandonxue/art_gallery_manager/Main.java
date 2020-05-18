package com.github.brandonxue.art_gallery_manager;

import java.awt.EventQueue;

import javax.swing.JFrame;

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

        Runnable r = () -> {
            JFrame frame = new JFrame("Art Gallery Manager");
            frame.add(QueryPanelBuilder.buildQueryPanel());
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        }; // See lambda functions and functional interfaces Java 8
        EventQueue.invokeLater(r);
    }
}