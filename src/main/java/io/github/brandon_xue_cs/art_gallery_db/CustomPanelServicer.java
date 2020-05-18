package io.github.brandon_xue_cs.art_gallery_db;

import java.lang.InterruptedException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.SwingWorker.StateValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ExecutionException;


import io.github.brandon_xue_cs.art_gallery_db.MySQLUtility;
import io.github.brandon_xue_cs.art_gallery_db.OutputPanelServicer;
import io.github.brandon_xue_cs.art_gallery_db.SimpleBroadcaster;
import io.github.brandon_xue_cs.art_gallery_db.SimpleListener;

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