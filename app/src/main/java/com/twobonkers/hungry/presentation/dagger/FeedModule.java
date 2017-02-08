package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.data.local.preferences.LocalUserRepository;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.FeedPager;
import com.twobonkers.hungry.presentation.details.RecipeChangeBus;
import com.twobonkers.hungry.presentation.feed.FeedViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedModule {

    @Provides @ActivityScope
    public FeedViewModel provideViewModel(FeedPager feedPager, RecipeChangeBus recipeChangeBus) {
        return new FeedViewModel(feedPager, recipeChangeBus);
    }

    @Provides @ActivityScope
    public FeedPager provideFeedPager(RecipesService recipesService, LocalUserRepository localUserRepository) {
        return new FeedPager(recipesService, localUserRepository);
    }
}
