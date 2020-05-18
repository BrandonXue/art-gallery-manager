package io.github.brandon_xue_cs.art_gallery_db;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class OutputPanelServicer {
    private DefaultTableModel tableModels[];
    private int activeModel;

    private SimpleBroadcaster modelUpdateBroadcaster;

    public OutputPanelServicer(int numModels) {
        // Initialize Array
        tableModels = new DefaultTableModel[numModels];

        // Initialize the table models inside the array with blank data
        Vector<Vector<Object>> blankData = new Vector<Vector<Object>>();
        Vector<Object> blankRow = new Vector<>();
        blankRow.add("");
        blankData.add(blankRow);
        for (int i = 0; i < numModels; ++i) {
            tableModels[i] = new DefaultTableModel(blankData, blankRow);
        }
        
        modelUpdateBroadcaster = new SimpleBroadcaster();
    }

    /**
     * Update a specific DefaultTableModel with data from a CachedRowSet.
     * Broadcasts a DefaultTableModel
     * @param rowSet data to be used for populating a DefaultTableModel.
     * @param modelToUpdate indicates which DefaultTableModel is to be updated.
     */
    public void updateModel(CachedRowSet rowSet, int modelToUpdate) {
        Vector<String> columnIdentifiers = new Vector<>(); // For storing TableModel column identifiers (headers)
        Vector<Vector<Object>> dataVector = new Vector<>(); // For storing TableModel data

        if (rowSet != null) {
            try {
                ResultSetMetaData metaData = rowSet.getMetaData();
            
                // Get the columnIdentifiers and dataVector using MySQLUtility static methods
                columnIdentifiers = MySQLUtility.metaDataToColumnVector(metaData);
                dataVector = MySQLUtility.rowSetToDataVector(rowSet);
            
                // Set the new data model for the table using our matrix and column header vector
                tableModels[modelToUpdate] = new DefaultTableModel(dataVector, columnIdentifiers);
    
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Error updating table");
            }
        }
        // If the currently active pane is equal to the updated pane, send a notification
        if (modelToUpdate == activeModel) {
            modelUpdateBroadcaster.broadcast(tableModels[modelToUpdate]);
        }
    }

    /**
     * Update the currently active DefaultTableModel. To be called when a new JPanel becomes active. 
     * @param newCurrentPane The index of the DefaultTableModel corresponding to the newly active JPanel.
     */
    public void paneChanged(int newCurrentPane) {
        if (newCurrentPane != activeModel) {
            activeModel = newCurrentPane;
            modelUpdateBroadcaster.broadcast(tableModels[activeModel]);
        }
    }

    /**
     * Adds a SimpleListener which will receive the newest DefaultTableModel update
     */
    public void addModelUpdateListener(SimpleListener l) {
        modelUpdateBroadcaster.addListener(l);
    }
}