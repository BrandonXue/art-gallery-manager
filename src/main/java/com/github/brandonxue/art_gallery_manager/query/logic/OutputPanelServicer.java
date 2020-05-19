package com.github.brandonxue.art_gallery_manager.query;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import java.util.Vector;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.sql.rowset.CachedRowSet;

import com.github.brandonxue.art_gallery_manager.event.*;
import com.github.brandonxue.art_gallery_manager.util.*;

@SuppressWarnings("serial")
public class OutputPanelServicer {
    private JTable tableReference;
    private DefaultTableModel tableModels[];
    private Vector<Integer> columnWidths[];
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
        for (int i = 0; i < numModels; ++i)
            tableModels[i] = new DefaultTableModel(blankData, blankRow);

        // Initialize the columnWidths array
        columnWidths = new Vector[numModels];
        for (int i = 0; i < numModels; ++i)
            columnWidths[i] = new Vector<>();
        
        modelUpdateBroadcaster = new SimpleBroadcaster();
    }

    /**
     * Resizes tableReference columns based off of the previous setting for the active DefaultTableModel stored in columnWidths.
     */
    private void refit() {
        // If previous settings have not been set, return
        if (columnWidths[activeModel].size() == 0) return;

        final int numCols = tableModels[activeModel].getColumnCount();
        final TableColumnModel columnModel = tableReference.getColumnModel();

        for (int i = 0; i < numCols; ++i)
            columnModel.getColumn(i).setPreferredWidth(columnWidths[activeModel].get(i));
    }

    /**
     * Determines the width of the columns for a particular DefaultTableModel and sets tableReference accordingly. The final widths of a column are dependent on the column name and the widest data element inside the column. The largest of the two, plus 10, is taken, and each maximum column width is limited to 200.
     * The resulting width is stored in 
     */
    private void fitTableToData() {
        // Clear previous settings
        columnWidths[activeModel].clear();

        final TableColumnModel columnModel = tableReference.getColumnModel();
        final int numCols = tableReference.getColumnCount();
        final int numRows = tableReference.getRowCount();

        // Need a FontMetrics instance to find String width
        // https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
        final FontMetrics fontMetrics = tableReference.getGraphics().getFontMetrics();

        int columnNameWidth, largerOfTwo;
        for (int col = 0; col < numCols; ++col) {

            // Find the width of the column name, add 10 to it.
            columnNameWidth = fontMetrics.stringWidth(tableModels[activeModel].getColumnName(col)) + 10;

            // Find the width of the widest data element in the column
            int maxDataWidth = 0;
            TableCellRenderer renderer;
            Component c;
            for (int row = 0; row < numRows; ++row) {
                // Get the renderer for each specific cell in the column 
                renderer = tableReference.getCellRenderer(row, col);
                // JTables use a single cell renderer for data of the same type, get the component uses as renderer
                // For this table the default should likely be a JLabel
                // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#editrender
                // Also see: https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
                c = tableReference.prepareRenderer(renderer, row, col);
                maxDataWidth = Math.max(c.getPreferredSize().width + 10, maxDataWidth); // Add 10 or it seems to cut off
            }
            
            // Use the larger value between column header width and max data width
            largerOfTwo = Math.max(maxDataWidth, columnNameWidth);
            
            // But if it's too big, truncate
            if(largerOfTwo > 200)
                largerOfTwo = 200;

            // Set the column width in the JTable and also store the result into a Vector
            columnModel.getColumn(col).setPreferredWidth(largerOfTwo);
            columnWidths[activeModel].add(new Integer(largerOfTwo));
        }
    }

    /**
     * Update a specific DefaultTableModel with data from a CachedRowSet. Broadcasts a DefaultTableModel.
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
            fitTableToData();
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
            refit();
        }
    }

    /**
     * Adds a SimpleListener which will receive the newest DefaultTableModel update
     */
    public void addModelUpdateListener(SimpleListener l) {
        modelUpdateBroadcaster.addListener(l);
    }

    /**
     * Set a reference to a JTable which will have its column widths manipulated every time its model is swapped or updated.
     */
    public void setTableReference(JTable table) {
        tableReference = table;
    }
}