package com.twobonkers.hungry.presentation.favourite;

import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.domain.GetSavedRecipesUseCase;
import com.twobonkers.hungry.presentation.views.FragmentViewModel;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class FavouritesViewModel extends FragmentViewModel<FavouritesFragment>
        implements FavouritesViewModelInputs, FavouritesViewModelOutputs {

    private BehaviorSubject<List<Recipe>> savedRecipes = BehaviorSubject.create();

    public final FavouritesViewModelInputs inputs = this;
    public final FavouritesViewModelOutputs outputs = this;

    public FavouritesViewModel(GetSavedRecipesUseCase savedRecipesUseCase) {
        savedRecipesUseCase.getSavedRecipes()
                .compose(bindToLifecycle())
                .subscribe(recipes -> savedRecipes.onNext(recipes));
    }

    @Override
    public Observable<List<Recipe>> savedRecipes() {
        return savedRecipes;
    }
}
