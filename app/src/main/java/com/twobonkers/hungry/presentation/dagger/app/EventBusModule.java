package com.twobonkers.hungry.presentation.dagger.app;

import com.twobonkers.hungry.presentation.details.RecipeChangeBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {

    @Provides @Singleton
    public RecipeChangeBus provideRecipeChangeBus() {
        return new RecipeChangeBus();
    }
}
