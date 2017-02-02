package com.twobonkers.hungry.data.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class AuthorModel {

    public abstract String id();
    public abstract String name();
    @Nullable public abstract String imageUrl();

    public static AuthorModel create(String id, String name, String imageUrl) {
        return new AutoValue_AuthorModel(id, name, imageUrl);
    }

    public static TypeAdapter<AuthorModel> typeAdapter(Gson gson) {
        return new AutoValue_AuthorModel.GsonTypeAdapter(gson).nullSafe();
    }
}
