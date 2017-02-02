package com.twobonkers.hungry.data;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RecipesService {

    @GET("recipes")
    Observable<GetRecipesResponse> getRecipes(@Query("page") int page);
}
