package com.twobonkers.hungry.data.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class Author implements Parcelable {

    public abstract String id();
    public abstract String name();
    @Nullable public abstract String imageUrl();

    public static Author create(String id, String name, String imageUrl) {
        return new AutoValue_Author(id, name, imageUrl);
    }
}
