package com.twobonkers.hungry.presentation.feed;

import com.twobonkers.hungry.data.models.Recipe;

import java.util.List;

import rx.Observable;

public interface FeedViewModelOutputs {

    /**
     * Emits all accumulated recipes.
     */
    Observable<List<Recipe>> recipes();

    /**
     * Emits that loading has started/ended.
     */
    Observable<Boolean> showLoading();

    /**
     * Emits that loading has failed and the user may retry.
     */
    Observable<Boolean> showRetry();

    /**
     * Emits that there are no more recipes to fetch.
     */
    Observable<Void> onLastPage();

    /**
     * Emits that an error occurred while fetching recipes.
     * @return
     */
    Observable<Throwable> error();
}
