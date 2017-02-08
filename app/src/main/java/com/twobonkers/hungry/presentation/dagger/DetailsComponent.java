package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.presentation.details.DetailsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(DetailsActivity activity);
}
