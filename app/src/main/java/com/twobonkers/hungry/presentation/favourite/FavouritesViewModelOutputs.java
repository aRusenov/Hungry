package com.twobonkers.hungry.presentation.favourite;

import android.database.Cursor;

import com.twobonkers.hungry.data.models.Recipe;

import java.util.List;

import rx.Observable;

public interface FavouritesViewModelOutputs {

    Observable<List<Recipe>> savedRecipes();
}
