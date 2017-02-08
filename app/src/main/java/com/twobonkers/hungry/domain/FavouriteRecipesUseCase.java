package com.twobonkers.hungry.domain;

import com.twobonkers.hungry.data.models.Recipe;

import rx.Observable;

public interface FavouriteRecipesUseCase {

    /**
     * Sends a favourite request to the API to set the recipe as either favourite / not favourite
     * depending on its current state.
     * Also saves the recipe locally if it's not in favourites. If it's
     * already in favourites, the recipe is removed from local storage.
     * @return if the recipe is saved or not
     */
    Observable<Boolean> favourite(Recipe recipe);
}
