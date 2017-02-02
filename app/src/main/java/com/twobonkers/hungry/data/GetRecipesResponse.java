package com.twobonkers.hungry.data;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.twobonkers.hungry.data.models.RecipeFeedModel;

import java.util.List;

@AutoValue
public abstract class GetRecipesResponse {

    public abstract List<RecipeFeedModel> recipes();
    public abstract int totalPages();
    public abstract int page();

    public static GetRecipesResponse create(List<RecipeFeedModel> recipes, int totalPages, int page) {
        return new AutoValue_GetRecipesResponse(recipes, totalPages, page);
    }

    public static TypeAdapter<GetRecipesResponse> typeAdapter(Gson gson) {
        return new AutoValue_GetRecipesResponse.GsonTypeAdapter(gson).nullSafe();
    }
}
