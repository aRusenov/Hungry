package com.twobonkers.hungry.domain;

import android.support.annotation.Nullable;

import com.twobonkers.hungry.data.local.LocalUserRepository;
import com.twobonkers.hungry.data.remote.PostFavouriteResponse;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.util.UserUtils;

import rx.Observable;

public class FavouriteRecipesUseCaseImpl implements FavouriteRecipeUseCase {

    private RecipesService recipesService;
    private @Nullable String authToken;

    public FavouriteRecipesUseCaseImpl(RecipesService recipesService, LocalUserRepository localUserRepository) {
        this.recipesService = recipesService;
        authToken = UserUtils.getBearerToken(localUserRepository.currentUser());
    }

    @Override
    public Observable<PostFavouriteResponse> favourite(long recipeId) {
        return recipesService.favouriteRecipe(recipeId, authToken);
    }
}
