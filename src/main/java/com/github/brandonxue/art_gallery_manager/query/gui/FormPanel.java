package com.github.brandonxue.art_gallery_manager.query;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.plaf.DimensionUIResource;

import com.github.brandonxue.art_gallery_manager.event.*;
import com.github.brandonxue.art_gallery_manager.util.*;

@SuppressWarnings("serial")
public class FormPanel extends JPanel {
    private final int NUM_COMBO_BOXES = 3;
    private JComboBox<String> tableSelector, searchAttributeSelector, orderingAttributeSelector;
    private JTextField searchEntryField;
    private JRadioButton orderAscending, orderDescending;
    private JButton queryButton;
    private SimpleListener updateListener;

    private FormPanelServicer servicer;

    public FormPanel() {
        setLayout(new GridBagLayout());

        buildTableSelectionComponents();
        buildSearchComponents();
        buildOrderingComponents();
        buildQueryButton();
    }

    /**
     * Build GUI components related to selecting tables (relations).
     */
    private void buildTableSelectionComponents() {
        // Build the label, combobox, and button for the table selector
        JLabel selectTableLabel = new JLabel("Select Table");
        GridBagConstraints c = new GridBagCBuilder(0, 0).sAnchor(GridBagConstraints.WEST);
        add(selectTableLabel, c);

        tableSelector = new JComboBox<>();
        ResizeUtility.setAbsoluteSize(tableSelector, 300, 30);
        c = new GridBagCBuilder(0, 1, 2).sAnchor(GridBagConstraints.WEST);
        add(tableSelector, c);
    }

    /**
     * Build GUI components related to searching by columns (attributes).
     */
    private void buildSearchComponents() {
        JLabel searchByLabel = new JLabel("Search By Attribute (Optional)");
        GridBagConstraints c = new GridBagCBuilder(0, 2).sAnchor(GridBagConstraints.WEST).sInsets(20, 0, 0, 0);
        add(searchByLabel, c);

        searchAttributeSelector= new JComboBox<>();
        ResizeUtility.setAbsoluteSize(searchAttributeSelector, 300, 30);
        c = new GridBagCBuilder(0, 3, 2).sAnchor(GridBagConstraints.WEST);
        add(searchAttributeSelector, c);

        searchEntryField = new JTextField();
        ResizeUtility.setAbsoluteSize(searchEntryField, 300, 30);
        c = new GridBagCBuilder(0, 4, 2).sAnchor(GridBagConstraints.WEST);
        add(searchEntryField, c);
    }

    /**
     * Build GUI components related to ordering the result set.
     */
    private void buildOrderingComponents() {
        // Add the label for the orderBySelector
        JLabel orderByLabel = new JLabel("Order By Attribute (Optional)");
        GridBagConstraints c = new GridBagCBuilder(0, 5).sAnchor(GridBagConstraints.WEST).sInsets(20, 0, 0, 0);
        add(orderByLabel, c);

        // Add the orderBySelector drop down box
        orderingAttributeSelector =  new JComboBox<>();
        ResizeUtility.setAbsoluteSize(orderingAttributeSelector, 300, 30);
        c = new GridBagCBuilder(0, 6, 2).sAnchor(GridBagConstraints.WEST);
        add(orderingAttributeSelector, c);

        // Add radio buttons for choosing sorting order.
        orderAscending = new JRadioButton("ascending");
        orderAscending.setSelected(true);
        c = new GridBagCBuilder(0, 7).sAnchor(GridBagConstraints.WEST);
        add(orderAscending, c);

        orderDescending = new JRadioButton("descending");
        c = new GridBagCBuilder(1, 7).sAnchor(GridBagCBuilder.WEST);
        add(orderDescending, c);

        ButtonGroup orderButtons = new ButtonGroup();
        orderButtons.add(orderAscending);
        orderButtons.add(orderDescending);
    }

    /**
     * Build the JButton used to submit the MySQL query.
     */
    private void buildQueryButton() {
        queryButton = new JButton("Submit Query");
        GridBagConstraints c = new GridBagCBuilder(0, 8, 2).sInsets(20, 0, 0, 0);
        add(queryButton, c);
    }

    /**
     * Add Listeners so that FormPanel's inputs can be connected to the servicer. Servicer must be set prior to this method being called.
     */
    private void addListeners() {
        tableSelector.addActionListener(e -> {
            servicer.setSelectedTable((String)tableSelector.getSelectedItem());
        });

        searchAttributeSelector.addActionListener(e -> {
            servicer.setSearchAttribute((String)searchAttributeSelector.getSelectedItem());
        });

        orderingAttributeSelector.addActionListener(e -> {
            servicer.setOrderingAttribute((String)orderingAttributeSelector.getSelectedItem());
        });

        searchEntryField.getDocument().addDocumentListener( new DocumentListener(){
            @Override
            public void removeUpdate(DocumentEvent e) {
                servicer.setSearchString(searchEntryField.getText());
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                servicer.setSearchString(searchEntryField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                servicer.setSearchString(searchEntryField.getText());
            }
        });
        
        orderAscending.addActionListener(e -> {servicer.setOrdering(true);});
        orderDescending.addActionListener(e -> {servicer.setOrdering(false);});
    
        queryButton.addActionListener(e -> {servicer.requestQuery();});
    }

    /**
     * Link this JPanel with its logical counterpart so that it can be pushed with view updates, and so that inputs from the user can be forwarded to the servicer. The servicer allows this class to focus solely on defining the layout of the GUI and setting up the communication.
     * @param s the FormPanelServicer that will function as this Pane's logical component
     */
    public void setServicer(FormPanelServicer s) {
        servicer = s;
        // Add listeners for own inputs to be connected to the servicer
        addListeners();
        // Add listeners for updating own view when servicer broadcasts updates
        s.addTableComboBoxListener(msg -> tableSelector.setModel((DefaultComboBoxModel)msg[0]));
        s.addAttributeComboBoxListener1(msg -> searchAttributeSelector.setModel((DefaultComboBoxModel)msg[0]));
        s.addAttributeComboBoxListener2(msg -> orderingAttributeSelector.setModel((DefaultComboBoxModel)msg[0]));
    }
}