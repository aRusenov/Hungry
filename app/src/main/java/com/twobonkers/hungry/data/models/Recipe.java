package com.twobonkers.hungry.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

import java.util.List;

@AutoValue
@AutoGson
public abstract class Recipe implements Parcelable {

    public abstract long id();
    public abstract String title();
    public abstract String previewImageUrl();
    public abstract List<Step> steps();
    public abstract List<Ingredient> ingredients();
    public abstract int favouriteCount();
    public abstract int prepTime();
    public abstract int portions();
    public abstract boolean favourited();

    public static Builder builder() {
        return new AutoValue_Recipe.Builder();
    }

    public Builder toBuilder() {
        return new AutoValue_Recipe.Builder(this);
    }

    public Recipe withFavouriteCount(int favCount, boolean favourited) {
        return toBuilder()
                .favouriteCount(favCount)
                .favourited(favourited)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        Recipe other = (Recipe) obj;
        return id() == other.id();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder title(String title);

        public abstract Builder previewImageUrl(String previewImageUrl);

        public abstract Builder steps(List<Step> steps);

        public abstract Builder ingredients(List<Ingredient> ingredients);

        public abstract Builder favouriteCount(int favouriteCount);

        public abstract Builder prepTime(int prepTime);

        public abstract Builder portions(int portions);

        public abstract Builder favourited(boolean favourited);

        public abstract Recipe build();
    }
}
