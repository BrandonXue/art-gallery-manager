package com.github.brandonxue.art_gallery_manager.event;

import java.util.Vector;

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