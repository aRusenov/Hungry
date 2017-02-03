package com.twobonkers.hungry.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class Recipe implements Parcelable {

    public abstract long id();
    public abstract String title();
    public abstract String previewImageUrl();
    public abstract Author author();
    public abstract int favouriteCount();
    public abstract int prepTime();
    public abstract boolean favourited();
}
