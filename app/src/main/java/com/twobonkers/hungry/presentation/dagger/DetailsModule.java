package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.data.local.database.DatabaseManager;
import com.twobonkers.hungry.data.local.preferences.LocalUserRepository;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.FavouriteRecipesUseCase;
import com.twobonkers.hungry.domain.FavouriteRecipesUseCaseImpl;
import com.twobonkers.hungry.presentation.details.DetailsViewModel;
import com.twobonkers.hungry.presentation.details.RecipeChangeBus;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsModule {

    @Provides @ActivityScope
    public DetailsViewModel provideViewModel(FavouriteRecipesUseCase favouriteRecipesUseCase, RecipeChangeBus recipeChangeBus) {
        return new DetailsViewModel(favouriteRecipesUseCase, recipeChangeBus);
    }

    @Provides @ActivityScope
    public FavouriteRecipesUseCase provideFavouriteRecipeUseCase(RecipesService recipesService,
                                                                 DatabaseManager databaseManager,
                                                                 LocalUserRepository localUserRepository) {
        return new FavouriteRecipesUseCaseImpl(recipesService, databaseManager, localUserRepository);
    }
}
