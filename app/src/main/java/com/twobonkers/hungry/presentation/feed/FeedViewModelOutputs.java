package com.twobonkers.hungry.presentation.feed;

import com.twobonkers.hungry.data.models.RecipeFeedModel;

import java.util.List;

import rx.Observable;

public interface FeedViewModelOutputs {

    Observable<List<RecipeFeedModel>> recipes();

    Observable<Boolean> showLoading();

    Observable<Boolean> showRetry();

    Observable<Boolean> enablePaging();

    Observable<Void> onLastPage();

    Observable<Throwable> error();
}
