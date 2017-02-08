package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.presentation.favourite.FavouritesFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FavouritesModule.class)
public interface FavouritesComponent {

    void inject(FavouritesFragment fragment);
}
