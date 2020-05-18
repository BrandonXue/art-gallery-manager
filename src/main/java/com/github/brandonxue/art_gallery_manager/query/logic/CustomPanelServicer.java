package com.github.brandonxue.art_gallery_manager;

import java.lang.InterruptedException;

import java.util.concurrent.ExecutionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.CachedRowSet;

import javax.swing.SwingWorker.StateValue;

@SuppressWarnings("serial")
public class CustomPanelServicer {
    private String inputText, errorIndicator;

    private SimpleBroadcaster errorBroadcaster;

    private OutputPanelServicer outputPanelServicer;

    public CustomPanelServicer() {
        inputText = errorIndicator = "";

        errorBroadcaster = new SimpleBroadcaster();
    }

    public void setInputText(String text) {
        inputText = text;
    }

    public void requestQuery() {
        Pattern p = Pattern.compile("(\\s)*(?i)(select)(.|\\s)+(?i)(from)(.|\\s)+");
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

    private void queryResultArrived(CachedRowSet result) {
        if (result != null) {
            errorBroadcaster.broadcast("");
            outputPanelServicer.updateModel(result, 1); // Model 1 corresponds to the CannedPanel's model
            return;
        }
    }

    public void addErrorListener(SimpleListener l) {
        errorBroadcaster.addListener(l);
    }

    public void addOutputPanelServicer(OutputPanelServicer s) {
        outputPanelServicer = s;
    }
}