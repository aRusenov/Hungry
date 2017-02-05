package com.twobonkers.hungry.presentation.details;

import android.util.Pair;

import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.domain.FavouriteRecipeUseCase;
import com.twobonkers.hungry.presentation.IntentKeys;
import com.twobonkers.hungry.presentation.views.ActivityViewModel;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class DetailsViewModel extends ActivityViewModel<DetailsActivity>
        implements DetailsViewModelInputs, DetailsViewModelOutputs {

    public final DetailsViewModelInputs inputs = this;
    public final DetailsViewModelOutputs outputs = this;

    private PublishSubject<Void> favouriteClick = PublishSubject.create();

    private BehaviorSubject<Recipe> recipe = BehaviorSubject.create();
    private PublishSubject<Throwable> error = PublishSubject.create();
    private BehaviorSubject<Boolean> loading = BehaviorSubject.create();

    public DetailsViewModel(FavouriteRecipeUseCase favouriteRecipeUseCase, RecipeChangeBus recipeChangeBus) {
        Observable<Recipe> recipe = intent()
                .map(intent -> intent.getParcelableExtra(IntentKeys.RECIPE))
                .ofType(Recipe.class)
                .cache();

        favouriteClick.compose(bindToLifecycle())
                // Debounce when there's already an in-flight request
                .filter(__ -> !loading.hasValue() || !loading.getValue())
                .withLatestFrom(recipe, (__, r) -> r)
                .doOnNext(__ -> loading.onNext(true))
                .flatMap(r -> favouriteRecipeUseCase.favourite(r.id())
                        .subscribeOn(Schedulers.io())
                        .map(response -> Pair.create(r, response))
                        .doOnError(t -> error.onNext(t))
                        .onErrorResumeNext(Observable.empty())
                        .doOnEach(__ -> loading.onNext(false))
                )
                .subscribe(res -> {
                    Recipe newRecipe = res.first.withFavourited(res.second.favourite());
                    recipeChangeBus.sendRecipeChange(newRecipe);
                    this.recipe.onNext(newRecipe);
                });
    }

    @Override
    public void clickFavourite() {
        favouriteClick.onNext(null);
    }

    @Override
    public Observable<Recipe> recipe() {
        return recipe;
    }

    @Override
    public Observable<Boolean> loading() {
        return loading;
    }

    @Override
    public Observable<Throwable> error() {
        return error;
    }
}
