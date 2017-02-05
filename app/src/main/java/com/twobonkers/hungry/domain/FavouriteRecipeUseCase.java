package com.twobonkers.hungry.domain;

import com.twobonkers.hungry.data.remote.PostFavouriteResponse;

import rx.Observable;

public interface FavouriteRecipeUseCase {

    Observable<PostFavouriteResponse> favourite(long recipeId);
}
