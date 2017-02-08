package com.twobonkers.hungry.presentation.dagger;

import com.twobonkers.hungry.presentation.feed.FeedFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FeedModule.class)
public interface FeedComponent {

    void inject(FeedFragment fragment);
}
