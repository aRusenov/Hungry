package com.twobonkers.hungry.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RecipesService {

    @GET("recipes")
    Observable<GetRecipesResponse> getRecipes(@Query("page") int page, @Header("Authorization") String token);

    @POST("recipes/{id}/favourite")
    Observable<PostFavouriteResponse> favouriteRecipe(@Path("id") long id, @Header("Authorization") String token);
}
