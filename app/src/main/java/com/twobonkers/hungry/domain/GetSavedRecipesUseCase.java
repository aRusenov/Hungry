package com.twobonkers.hungry.domain;

import com.twobonkers.hungry.data.models.Recipe;

import java.util.List;

import rx.Observable;

public interface GetSavedRecipesUseCase {

    Observable<List<Recipe>> getSavedRecipes();
}
