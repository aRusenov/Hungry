package com.twobonkers.hungry.data;

import com.google.auto.value.AutoValue;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.domain.lib.autogson.AutoGson;

import java.util.List;

@AutoValue
@AutoGson
public abstract class GetRecipesResponse {

    public abstract List<Recipe> recipes();
    public abstract int totalPages();
    public abstract int page();

    public static GetRecipesResponse create(List<Recipe> recipes, int totalPages, int page) {
        return new AutoValue_GetRecipesResponse(recipes, totalPages, page);
    }
}
