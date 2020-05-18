package com.github.brandonxue.art_gallery_manager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.plaf.DimensionUIResource;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class CustomPanel extends JPanel {
    private JLabel errorIndicatorLabel;
    private JTextArea inputTextArea;
    private JButton submitButton;

    CustomPanelServicer servicer;

    public CustomPanel() {
        setLayout(new GridBagLayout());

        errorIndicatorLabel = new JLabel("");
        GridBagConstraints c = new GridBagCBuilder(0, 0);
        add(errorIndicatorLabel, c);

        inputTextArea = new JTextArea("Please enter a MySQL query.");
        ResizeUtility.setAbsoluteSize(inputTextArea, 500, 200);
        c = new GridBagCBuilder(0, 1);
        add(inputTextArea, c);

        submitButton = new JButton("Submit Query");
        c = new GridBagCBuilder(0, 2);
        add(submitButton, c);
    }

    private void addActionListeners() {
        submitButton.addActionListener(l -> {
            servicer.setInputText(inputTextArea.getText());
            servicer.requestQuery();
        });
    }

    public void setServicer(CustomPanelServicer s) {
        servicer = s;
        addActionListeners();
        servicer.addErrorListener(msgs -> {
            errorIndicatorLabel.setText((String)msgs[0]);
        });
    }
}