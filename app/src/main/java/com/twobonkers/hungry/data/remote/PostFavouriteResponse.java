package com.twobonkers.hungry.data.remote;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class PostFavouriteResponse {

    public abstract String recipeId();
    public abstract boolean favourite();
}
