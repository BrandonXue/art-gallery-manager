package com.github.brandonxue.art_gallery_manager.query;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.plaf.DimensionUIResource;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.brandonxue.art_gallery_manager.util.*;

@SuppressWarnings("serial")
public class CustomPanel extends JPanel {
    private JLabel errorIndicatorLabel;
    private JTextArea inputTextArea;
    private JButton submitButton;

    CustomPanelServicer servicer;

    public CustomPanel() {
        setLayout(new GridBagLayout());

        // Create errorIndicatorLabel. Used to communicate simply messages with the user.
        // It remains an empty String until users enter an invalid query.
        errorIndicatorLabel = new JLabel("");
        GridBagConstraints c = new GridBagCBuilder(0, 0);
        add(errorIndicatorLabel, c);

        // Create the JTextArea where users will input their query.
        inputTextArea = new JTextArea("Please enter a MySQL query.");
        JScrollPane textScrollPane = new JScrollPane(); // Place the JTextArea inside this JScrollPane
        ResizeUtility.setAbsoluteSize(textScrollPane, 500, 200);
        textScrollPane.setViewportView(inputTextArea);
        c = new GridBagCBuilder(0, 1);
        add(textScrollPane, c);

        // Used for submitting the query
        submitButton = new JButton("Submit Query");
        c = new GridBagCBuilder(0, 2);
        add(submitButton, c);
    }

    /**
     * Add Listeners so that CustomPanel's inputs can be connected to the servicer. Servicer must be set prior to this method being called.
     */
    private void addListeners() {
        submitButton.addActionListener(l -> {
            servicer.setInputText(inputTextArea.getText());
            servicer.requestQuery();
        });
    }

    /**
     * Link this JPanel with its logical counterpart so that it can be pushed with view updates, and so that inputs from the user can be forwarded to the servicer. The servicer allows this class to focus solely on defining the layout of the GUI and setting up the communication.
     * @param s the CustomPanelServicer that will function as this Pane's logical component
     */
    public void setServicer(CustomPanelServicer s) {
        servicer = s;
        // Add listeners for own inputs to be connected to the servicer
        addListeners();
        // Add listeners for updating own view when servicer broadcasts updates
        servicer.addErrorListener(msgs -> {
            errorIndicatorLabel.setText((String)msgs[0]);
        });
    }
}