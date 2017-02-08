package com.twobonkers.hungry.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class Step implements Parcelable {

    public abstract String description();
    public abstract int order();

    public static Builder builder() {
        return new AutoValue_Step.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder description(String description);

        public abstract Builder order(int order);

        public abstract Step build();
    }
}
