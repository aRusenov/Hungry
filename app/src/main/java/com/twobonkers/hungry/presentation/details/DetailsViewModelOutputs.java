package com.twobonkers.hungry.presentation.details;

import com.twobonkers.hungry.data.models.Recipe;

import rx.Observable;

public interface DetailsViewModelOutputs {

    Observable<Recipe> recipe();

    Observable<Boolean> loading();

    Observable<Throwable> error();
}
