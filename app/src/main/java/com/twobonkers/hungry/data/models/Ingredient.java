package com.twobonkers.hungry.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class Ingredient implements Parcelable {

    public static class Measurements {
        public static final int GRAM = 0, KG = 1, ML = 2, L = 3,
                TEASPOON = 4, TABLESPOON = 5, GLASS = 6, PINCH = 7;
    }

    public abstract String name();
    public abstract int quantity();
    public abstract int measurement();

    public static Builder builder() {
        return new AutoValue_Ingredient.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {

        public abstract Builder name(String name);

        public abstract Builder quantity(int quantity);

        public abstract Builder measurement(int measurement);

        public abstract Ingredient build();
    }
}
