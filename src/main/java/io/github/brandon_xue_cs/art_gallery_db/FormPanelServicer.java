package io.github.brandon_xue_cs.art_gallery_db;

import java.awt.EventQueue;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

import java.util.concurrent.ExecutionException;

import java.lang.InterruptedException;


import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker.StateValue;

import io.github.brandon_xue_cs.art_gallery_db.MySQLUtility;
import io.github.brandon_xue_cs.art_gallery_db.OutputPanelServicer;
import io.github.brandon_xue_cs.art_gallery_db.SimpleBroadcaster;
import io.github.brandon_xue_cs.art_gallery_db.SimpleListener;
import javafx.event.Event;

@SuppressWarnings("serial")
public class FormPanelServicer {
    public static final int TABLE_COMBO_BOX_CHANGE = 0;
    public static final int ATTRIBUTE_COMBO_BOX_CHANGE = 0;
    
    private DefaultComboBoxModel<String> tableSelector, attributeSelector1, attributeSelector2;
    private DefaultButtonModel ascending, descending;
    private String selectedTable, selectedSearchAttribute, searchString, selectedOrderingAttribute;

    private SimpleBroadcaster broadcaster1, broadcaster2, broadcaster3;

    private OutputPanelServicer outputPanelServicer;
    
    public FormPanelServicer() {
        tableSelector = new DefaultComboBoxModel<>();
        attributeSelector1 = new DefaultComboBoxModel<>();
        attributeSelector2 = new DefaultComboBoxModel<>();

        ascending = new DefaultButtonModel();
        descending = new DefaultButtonModel();
        ascending.setPressed(true);

        selectedTable = searchString = "";
        selectedSearchAttribute = selectedOrderingAttribute = "-";

        broadcaster1 = new SimpleBroadcaster();
        broadcaster2 = new SimpleBroadcaster();
        broadcaster3 = new SimpleBroadcaster();
    }

    /**
     * Called once to initially populate the tableSelector with tables from the database.
     * Uses a SQLUtility to spawn a SwingWorker for query. When results arrive, this method calls
     * updateArrivedTableSelectorComboBox.
     */
    private void initTableSelectorComboBox() {
        MySQLUtility workerUtility = new MySQLUtility("SHOW TABLES"); // Use a SwingWorker
        
        // Set a property change listener to call update arrived once the query resolves.
        workerUtility.addPropertyChangeListener(e -> {
            if (e.getNewValue() == StateValue.DONE) {
                try {
                    updateArrivedTableSelectorComboBox(workerUtility.get());
                } catch (InterruptedException intrpEx) {}
                catch (ExecutionException execEx) {}
            }
        });
        workerUtility.execute();
    }

    /**
     * When initTableSelectorComboBox's SwingWorker is done, a property change listener will call this method.
     */
    private void updateArrivedTableSelectorComboBox(CachedRowSet tableResultSet) {
        if (tableResultSet == null) return;

        try {
            tableResultSet.beforeFirst();
            while (tableResultSet.next())
                tableSelector.addElement(tableResultSet.getString(1));
            tableSelector.setSelectedItem(null);
            broadcaster1.broadcast(tableSelector);
        } catch (SQLException ex) {
            System.out.println("Error retrieving tables.");
        }
    }

    /**
     * Triggered when selectedTable changes. Attempts to find the elements that the combobox should
     * be repopulated with.
     * @param selected the name of the table that is selected.
     */
    private void updateAttributeSelectorComboBox() {
        String query = "SELECT COLUMN_NAME, DATA_TYPE " +
                        "FROM information_schema.COLUMNS " +
                        "WHERE TABLE_SCHEMA = \"" + MySQLUtility.HEROKU_SCHEMA + "\"" +
                        " AND TABLE_NAME = \"" + selectedTable + "\"";
        MySQLUtility workerUtility = new MySQLUtility(query); // Use a SwingWorker
        
        // Set a property change listener to call update arrived once the query resolves.
        workerUtility.addPropertyChangeListener(e -> {
            if (e.getNewValue() == StateValue.DONE) {  
                try {
                    System.out.println("triggered");
                    updateArrivedAttributeSelectorComboBox(workerUtility.get());
                } catch (InterruptedException intrpEx) {}
                catch (ExecutionException execEx) {}
            }
        });
        workerUtility.execute();
    }

    /**
     * Called by updateAttributeSelectorComboBox to update the results into the ComboBoxModel.
     * @param result a result set of attributes that should be placed into the combo boxes.
     */
    private void updateArrivedAttributeSelectorComboBox(CachedRowSet attributeResultSet) {
        attributeSelector1.removeAllElements();
        attributeSelector2.removeAllElements();
        attributeSelector1.addElement("-");    
        attributeSelector2.addElement("-");  

        if (attributeResultSet != null) {
            try {  
                attributeResultSet.beforeFirst();               
                while (attributeResultSet.next()) {
                    attributeSelector1.addElement(attributeResultSet.getString(1));
                    attributeSelector2.addElement(attributeResultSet.getString(1));
                }
            } catch (SQLException e) {
                System.out.println("Error getting elements for attributeSelectors.");
            }
        }

        broadcaster2.broadcast(attributeSelector1);
        broadcaster3.broadcast(attributeSelector2);
    }

    public void setSelectedTable(String selected) {
        selectedTable = selected;
        updateAttributeSelectorComboBox();
    }

    public void setSearchAttribute(String selected) {
        selectedSearchAttribute = selected;
    }

    public void setOrderingAttribute(String selected) {
        selectedOrderingAttribute = selected;
    }

    public void setSearchString(String input) {
        searchString = input;
    }

    /**
     * @param b sets DefaultButtonModels. Ascending as b and descending as !b
     */
    public void setOrdering(boolean b) {
        ascending.setPressed(b);
        descending.setPressed(!b);
    }

    public void requestQuery() {
        String selectClause = "SELECT *";
        String fromClause;
        String whereClause = "";    // WHERE ... = ...
        String orderByClause = "";  // ORDER BY ... [ASC|DESC]

        // If there is no selected table, return early
        if (selectedTable.equals(""))
            return;

        fromClause = " FROM " + selectedTable;

        // If there is a selected search attribute and the search field is not empty
        if (!(selectedSearchAttribute.equals("-") || searchString.equals("")))
            whereClause = " WHERE " + selectedSearchAttribute + " = \"" + searchString + "\"";
        
            // If there is a selected order attribute
        if (!selectedOrderingAttribute.equals("-"))
            orderByClause = " ORDER BY " + selectedOrderingAttribute + " " + (ascending.isPressed() ? "ASC" : "DESC");

        // Use a SwingWorker
        MySQLUtility workerUtility = new MySQLUtility(selectClause + fromClause + whereClause + orderByClause);
        workerUtility.addPropertyChangeListener(e -> {
            if (e.getNewValue() == StateValue.DONE) {
                try {
                    // Model 0 corresponds to the FormPanel's model
                    outputPanelServicer.updateModel(workerUtility.get(), 0);
                } catch (InterruptedException intrpEx) {}
                catch (ExecutionException execEx) {}
            }
        });
        workerUtility.execute();
    }

    public void addTableComboBoxListener(SimpleListener l) {
        broadcaster1.addListener(l);
        initTableSelectorComboBox();
    }

    public void addAttributeComboBoxListener1(SimpleListener l) {
        broadcaster2.addListener(l);
    }

    public void addAttributeComboBoxListener2(SimpleListener l ) {
        broadcaster3.addListener(l);
    }

    public void addOutputPanelServicer(OutputPanelServicer s) {
        outputPanelServicer = s;
    }
}