package com.twobonkers.hungry.presentation.feed;

import com.twobonkers.hungry.data.GetRecipesResponse;
import com.twobonkers.hungry.data.RecipesService;
import com.twobonkers.hungry.data.models.RecipeFeedModel;
import com.twobonkers.hungry.domain.ApiPager;
import com.twobonkers.hungry.domain.lib.Transformers;
import com.twobonkers.hungry.presentation.views.BaseViewModel;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class FeedViewModel extends BaseViewModel<FeedFragment> implements FeedViewModelInputs, FeedViewModelOutputs {

    public final FeedViewModelInputs inputs = this;
    public final FeedViewModelOutputs outputs = this;

    private PublishSubject<Void> loadMore = PublishSubject.create();
    private PublishSubject<Integer> startOver = PublishSubject.create();
    private BehaviorSubject<List<RecipeFeedModel>> recipes = BehaviorSubject.create(Collections.emptyList());
    private BehaviorSubject<Boolean> showRetry = BehaviorSubject.create();
    private BehaviorSubject<Integer> nextPage = BehaviorSubject.create(0);

    private PublishSubject<Void> retryClicked = PublishSubject.create();
    private PublishSubject<Void> refresh = PublishSubject.create();

    private Observable<Boolean> loading;
    private Observable<Throwable> error;
    private Observable<Void> lastPage;

    public FeedViewModel(RecipesService service) {
        ApiPager<GetRecipesResponse, GetRecipesResponse, Integer> apiPager =
                ApiPager.<GetRecipesResponse, GetRecipesResponse, Integer>builder()
                        .loadMore(loadMore)
                        .startOver(startOver)
                        .makeRequest(service::getRecipes)
                        .mapResponse(response -> response)
                        .concater((total, page) -> {
                            total.recipes().addAll(page.recipes());
                            return total;
                        })
                        .stopWhen(response -> response.page() >= response.totalPages())
                        .nextPageParams(page -> page + 1)
                        .build();

        apiPager.pages()
                .compose(bindToLifecycle())
                .doOnNext(response -> nextPage.onNext(response.page()))
                .map(GetRecipesResponse::recipes)
                .subscribe(recipes);

        loading = apiPager.loading()
                .compose(bindToLifecycle());

        error = apiPager.error()
                .compose(bindToLifecycle());
        error.subscribe(__ -> showRetry.onNext(true));

        lastPage = apiPager.lastPage()
                .compose(bindToLifecycle())
                .cache();

        nextPage.compose(bindToLifecycle())
                .compose(Transformers.takeWhen(retryClicked))
                .subscribe(page -> {
                    showRetry.onNext(false);
                    startOver.onNext(page + 1);
                });

        refresh.subscribe(__ -> startOver.onNext(nextPage.getValue() + 1));
        startOver.onNext(nextPage.getValue() + 1);
    }

    @Override
    public void nextPage() {
        loadMore.onNext(null);
    }

    @Override
    public void refresh() {
        refresh.onNext(null);
    }

    @Override
    public void clickRetry() {
        retryClicked.onNext(null);
    }

    @Override
    public Observable<Void> onLastPage() {
        return lastPage;
    }

    @Override
    public Observable<Boolean> showRetry() {
        return showRetry;
    }

    @Override
    public Observable<Boolean> enablePaging() {
        return Observable.never();
    }

    @Override
    public Observable<Boolean> showLoading() {
        return loading;
    }

    @Override
    public Observable<List<RecipeFeedModel>> recipes() {
        return recipes;
    }

    @Override
    public Observable<Throwable> error() {
        return error;
    }
}
