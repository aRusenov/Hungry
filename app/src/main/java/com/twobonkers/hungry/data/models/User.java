package com.twobonkers.hungry.data.models;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

@AutoValue
@AutoGson
public abstract class User {

    public abstract long id();
    public abstract String token();
    public abstract String username();

    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder token(String token);

        public abstract Builder username(String username);

        public abstract User build();
    }
}
