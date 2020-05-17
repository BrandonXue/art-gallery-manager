package io.github.brandon_xue_cs.art_gallery_db;

import java.sql.Statement;
import java.util.Vector;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;

import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class MySQLUtility {

    public MySQLUtility() {}

    public static CachedRowSet executeDBCommand(String commandString) {
        // Establish a connection with the database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ART_GALLERY?serverTimezone=America/Los_Angeles", "test_user", "test_password");
        } catch (SQLException e) {
            System.out.println("Connection error:");
            System.out.println(e.toString());
            return null;
        }

        // Create a statement and execute the query
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(commandString);

            if (rs == null)
                return null;

            // Set the result to instance variables of the controller
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            return crs;

        } catch (SQLException e) {
            System.out.println("Statement execution error. Statement attempted:");
            System.out.println(commandString);
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println("Resource closing error.");
            }
        }
    }

    /**
     * Get a Vector of Strings representing the column identifiers for a given RowSetMetaData
     * @param metaData A non-null RowSetMetaData object.
     * @return The column labels of the RowSetMetaData in a String-Vector
     */
    public static Vector<String> metaDataToColumnVector(ResultSetMetaData metaData) throws SQLException {
        Vector<String> columnIdentifiers = new Vector<>();
        int colCount = metaData.getColumnCount();
        for (int i = 1; i <= colCount; ++i)
            columnIdentifiers.add(metaData.getColumnLabel(i));
        return columnIdentifiers;
    }

    /**
     * Get a Vector-composed matrix of CachedRowSet data.
     * @param rowSet A CachedRowSet of data to be used to populate the matrix.
     * @return The data of the CachedRowSet as a Vector of Object-Vectors.
     */
    public static Vector<Vector<Object>> rowSetToDataVector(CachedRowSet rowSet) throws SQLException {
        Vector<Vector<Object>> dataVector = new Vector<>();
        int colCount = rowSet.getMetaData().getColumnCount();
        while (rowSet.next()) {
            Vector<Object> rowData = new Vector<>(); // Create a Vector for each row
            for (int i = 1; i <= colCount; ++i)
                rowData.add(rowSet.getObject(i)); // Add datum to the row Vector
            dataVector.add(rowData); // Add the row Vector to the data Vector
        }
        return dataVector;
    }
}