package com.twobonkers.hungry.presentation.details;

import com.twobonkers.hungry.data.models.Recipe;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RecipeChangeBus {

    private PublishSubject<Recipe> recipeChanged = PublishSubject.create();

    public void sendRecipeChange(Recipe recipe) {
        recipeChanged.onNext(recipe);
    }

    public Observable<Recipe> recipeChanged() {
        return recipeChanged.asObservable();
    }
}
