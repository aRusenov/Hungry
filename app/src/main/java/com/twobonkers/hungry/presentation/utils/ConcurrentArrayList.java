package com.twobonkers.hungry.presentation.utils;

import java.util.ArrayList;
import java.util.Collection;

public class ConcurrentArrayList<T> {

    private ArrayList<T> list;

    public ConcurrentArrayList() {
        list = new ArrayList<T>();
    }

    public synchronized void addAll(Collection<T> collection) {
        list.addAll(collection);
    }

    public synchronized boolean replace(T newValue) {
        int pos = list.indexOf(newValue);
        if (pos == -1) {
            return false;
        }

        list.set(pos, newValue);
        return true;
    }

    public synchronized void clear() {
        list.clear();
    }

    public synchronized ArrayList<T> toList() {
        return new ArrayList<>(this.list);
    }
}
