package com.twobonkers.hungry.presentation.utils;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;

import java.util.UUID;

public class LifecycleRetainer {

    private static final String KEY_ID = "retained_id";

    private static LifecycleRetainer instance;

    private SimpleArrayMap<String, Object> retainedObjects;

    private LifecycleRetainer() {
        retainedObjects = new ArrayMap<>();
    }

    public static LifecycleRetainer getInstance() {
        if (instance == null) {
            instance = new LifecycleRetainer();
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return null;
        }

        String id = savedInstanceState.getString(KEY_ID);
        return id == null ? null : (T) retainedObjects.get(id);
    }

    public void save(Object obj, Bundle outState) {
        String id = findIdForObject(obj);
        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        retainedObjects.put(id, obj);
        outState.putString(KEY_ID, id);
    }

    public void remove(Object obj) {
        for (int i = 0; i < retainedObjects.size(); i++) {
            if (retainedObjects.valueAt(i) == obj) {
                retainedObjects.removeAt(i);
                break;
            }
        }
    }

    private String findIdForObject(Object obj) {
        for (int i = 0; i < retainedObjects.size(); i++) {
            if (retainedObjects.valueAt(i) == obj) {
                return retainedObjects.keyAt(i);
            }
        }

        return null;
    }
}
