package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.data.local.database.DatabaseManager;
import com.twobonkers.hungry.domain.GetSavedRecipesUseCase;
import com.twobonkers.hungry.domain.GetSavedRecipesUseCaseImpl;
import com.twobonkers.hungry.presentation.favourite.FavouritesViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FavouritesModule {

    @Provides @ActivityScope
    public FavouritesViewModel provideViewModel(GetSavedRecipesUseCase savedRecipesUseCase) {
        return new FavouritesViewModel(savedRecipesUseCase);
    }

    @Provides @ActivityScope
    public GetSavedRecipesUseCase provideUseCase(DatabaseManager databaseManager) {
        return new GetSavedRecipesUseCaseImpl(databaseManager);
    }
}
