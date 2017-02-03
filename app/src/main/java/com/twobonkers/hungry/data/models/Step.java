package com.twobonkers.hungry.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class Step implements Parcelable {

    public abstract String description();
    public abstract int order();
}
