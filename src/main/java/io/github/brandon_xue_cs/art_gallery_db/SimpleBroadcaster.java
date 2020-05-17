package io.github.brandon_xue_cs.art_gallery_db;

import java.util.Vector;

import io.github.brandon_xue_cs.art_gallery_db.SimpleListener;

@SuppressWarnings("serial")
public class SimpleBroadcaster {
    Vector<SimpleListener> listeners;

    public SimpleBroadcaster() {
        listeners = new Vector<>();
    }

    public void broadcast(Object... messages) {
        for (SimpleListener l : listeners) {
            l.update(messages);
        }
    }

    public void addListener(SimpleListener l) {
        listeners.add(l);
    }
}