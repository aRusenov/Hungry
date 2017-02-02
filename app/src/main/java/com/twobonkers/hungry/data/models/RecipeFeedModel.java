package com.twobonkers.hungry.data.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RecipeFeedModel {

    public abstract long id();
    public abstract String title();
    @SerializedName("previewImageUrl") public abstract String imageUrl();
    public abstract AuthorModel author();
    public abstract int favouriteCount();
    public abstract int prepTime();
    public abstract boolean favourited();

    public static RecipeFeedModel create(long id, String title, String imageUrl, AuthorModel author, int favouriteCount, int prepTime, boolean favourited) {
        return new AutoValue_RecipeFeedModel(id, title, imageUrl, author, favouriteCount, prepTime, favourited);
    }

    public static TypeAdapter<RecipeFeedModel> typeAdapter(Gson gson) {
        return new AutoValue_RecipeFeedModel.GsonTypeAdapter(gson).nullSafe();
    }
}
