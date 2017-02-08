package com.twobonkers.hungry.presentation.dagger.app;

import com.twobonkers.hungry.presentation.dagger.DetailsComponent;
import com.twobonkers.hungry.presentation.dagger.DetailsModule;
import com.twobonkers.hungry.presentation.dagger.FavouritesComponent;
import com.twobonkers.hungry.presentation.dagger.FavouritesModule;
import com.twobonkers.hungry.presentation.dagger.FeedComponent;
import com.twobonkers.hungry.presentation.dagger.FeedModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { BaseModule.class, NetModule.class, LocalStorageModule.class, EventBusModule.class })
public interface AppComponent {

    FeedComponent plus(FeedModule module);

    DetailsComponent plus(DetailsModule module);

    FavouritesComponent plus(FavouritesModule module);
}
