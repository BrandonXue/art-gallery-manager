package com.github.brandonxue.art_gallery_manager.query;

import java.lang.InterruptedException;

import java.util.concurrent.ExecutionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.CachedRowSet;

import javax.swing.SwingWorker.StateValue;

import com.github.brandonxue.art_gallery_manager.event.*;
import com.github.brandonxue.art_gallery_manager.util.*;

@SuppressWarnings("serial")
public class CustomPanelServicer {
    private String inputText, errorIndicator;

    private SimpleBroadcaster errorBroadcaster;

    private OutputPanelServicer outputPanelServicer;

    public CustomPanelServicer() {
        inputText = errorIndicator = "";

        errorBroadcaster = new SimpleBroadcaster();
    }

    /**
     * Receives an input corresponding to the query that the user inputted. This function is called at a high frequency.
     * @param text the user's input String.
     */
    public void setInputText(String text) {
        inputText = text;
    }

    /**
     * Receives an input which requests for a query to be performed. Basic regex will check for a select statement. If the statement is valid a SwingWorker will be spawned to work on the query.
     */
    public void requestQuery() {
        final Pattern p = Pattern.compile("(\\s)*(?i)(select)(.|\\s)+(?i)(from)(.|\\s)+");
        Matcher m = p.matcher(inputText);
        if (m.matches()) {
            MySQLUtility workerUtility = new MySQLUtility(inputText);
            workerUtility.addPropertyChangeListener(e -> {
                if (e.getNewValue() == StateValue.DONE) {
                    try {
                        queryResultArrived(workerUtility.get());
                    } catch (InterruptedException intrpEx) {}
                    catch (ExecutionException execEx) {}
                }
            });
            workerUtility.execute();
        } else 
            errorBroadcaster.broadcast("Please enter a valid SELECT statement.");
    }

    /**
     * When requestQuery's SwingWorker is done, this method will be triggered. A broadcast will be made to clear any error messages. The output will be sent to the output handling servicer.
     * @param result the CachedRowSet containing the results of the query. Will be forwarded to the output handling servicer.
     */
    private void queryResultArrived(CachedRowSet result) {
        if (result != null) {
            errorBroadcaster.broadcast("");
            outputPanelServicer.updateModel(result, 1); // Model 1 corresponds to the CannedPanel's model
            return;
        }
    }

    /**
     * Receives a SimpleListener that will receive a broadcast when an error is detected with the input query.
     * @param l
     */
    public void addErrorListener(SimpleListener l) {
        errorBroadcaster.addListener(l);
    }

    /**
     * Connect to an output handling servicer.
     */
    public void addOutputPanelServicer(OutputPanelServicer s) {
        outputPanelServicer = s;
    }
}