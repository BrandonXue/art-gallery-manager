package io.github.brandon_xue_cs.art_gallery_db;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;

import io.github.brandon_xue_cs.art_gallery_db.MySQLUtility;
import io.github.brandon_xue_cs.art_gallery_db.OutputPanelServicer;
import io.github.brandon_xue_cs.art_gallery_db.SimpleBroadcaster;
import io.github.brandon_xue_cs.art_gallery_db.SimpleListener;

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

    private void initTableSelectorComboBox() {
        CachedRowSet tableResultSet = MySQLUtility.executeDBCommand("SHOW TABLES");
        try {
            while (tableResultSet.next())
                tableSelector.addElement(tableResultSet.getString(1));
            tableSelector.setSelectedItem(null);
            broadcaster1.broadcast(tableSelector);
        } catch (SQLException ex) {
            System.out.println("Error retrieving tables.");
        }
    }

    /**
     * Triggered when selectedTable changes. Causes the attribute combo box model to repopulate with
     * attributes from the newly selected table.
     * @param selected the name of the table that is selected.
     */
    private void updateAttributeComboBox(String selected) {
        selectedTable = selected;
        String query = "SELECT COLUMN_NAME, DATA_TYPE " +
                        "FROM information_schema.COLUMNS " +
                        "WHERE TABLE_SCHEMA = \"ART_GALLERY\"" +
                        " AND TABLE_NAME = \"" + selectedTable + "\"";
        CachedRowSet attributeResultSet = MySQLUtility.executeDBCommand(query);
        
        if (attributeResultSet == null)
            return;
        
        attributeSelector1.removeAllElements();
        attributeSelector2.removeAllElements();
        attributeSelector1.addElement("-");    
        attributeSelector2.addElement("-");  
        
        try {                      
            while (attributeResultSet.next()) {
                attributeSelector1.addElement(attributeResultSet.getString(1));
                attributeSelector2.addElement(attributeResultSet.getString(1));
            }

        } catch (SQLException e) {}

        broadcaster2.broadcast(attributeSelector1);
        broadcaster3.broadcast(attributeSelector2);
    }

    public void setSelectedTable(String selected) {
        selectedTable = selected;
        updateAttributeComboBox(selected);
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

        CachedRowSet result = MySQLUtility.executeDBCommand(selectClause + fromClause + whereClause + orderByClause);
        outputPanelServicer.updateModel(result, 0); // Model 0 corresponds to the FormPanel's model
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