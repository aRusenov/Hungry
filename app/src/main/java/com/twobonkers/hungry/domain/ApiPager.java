package com.twobonkers.hungry.domain;


import com.twobonkers.hungry.domain.lib.Transformers;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class ApiPager<Data, Response, Params> {

    private PublishSubject<Boolean> loadSubject = PublishSubject.create();
    private PublishSubject<Void> onLastPage = PublishSubject.create();
    private PublishSubject<Params> nextPage = PublishSubject.create();
    private PublishSubject<Throwable> errorSubject = PublishSubject.create();

    private Func1<Params, Observable<Response>> makeRequest;
    private Func1<Data, Boolean> stopWhen;
    private Func1<Response, Data> mapResponse;
    private Func1<Params, Params> nextPageParams;
    private Observable<Void> loadMore;
    private Observable<Params> startOver;
    private Func2<Data, Data, Data> concater;

    private Observable<Data> pagingObservable;

    public ApiPager(Func1<Params, Observable<Response>> makeRequest,
                    Func1<Data, Boolean> stopWhen,
                    Func1<Response, Data> mapResponse,
                    Func1<Params, Params> nextPageParams,
                    Observable<Void> loadMore,
                    Observable<Params> startOver,
                    Func2<Data, Data, Data> concater) {
        this.makeRequest = makeRequest;
        this.stopWhen = stopWhen;
        this.loadMore = loadMore;
        this.mapResponse = mapResponse;
        this.nextPageParams = nextPageParams;
        this.startOver = startOver;
        this.concater = concater;

        pagingObservable = this.startOver
                .doOnNext(__ -> Timber.d("Begin"))
                .switchMap(this::paginate)
                .scan(concater)
                .doOnTerminate(() -> Timber.d("Switch map terminated"));
    }

    public Observable<Boolean> loading() {
        return loadSubject.asObservable();
    }

    public Observable<Void> lastPage() {
        return onLastPage.asObservable();
    }

    public Observable<Data> pages() {
        return pagingObservable;
    }

    public Observable<Throwable> error() {
        return errorSubject.asObservable();
    }

    private Observable<Data> paginate(Params startParams) {
        return nextPageObservable(startParams)
                .switchMap(this::fetchData)
                .takeUntil(stopWhen)
                .doOnTerminate(() -> onLastPage.onNext(null));
    }

    private Observable<Params> nextPageObservable(Params startParams) {
        return nextPage
                .compose(Transformers.takeWhen(loadMore))
                .startWith(startParams);
    }

    private Observable<Data> fetchData(final Params params) {
        return makeRequest.call(params)
                .subscribeOn(Schedulers.io())
                .doOnNext(__ -> {
                    Timber.d("Page successfully fetched!");
                    nextPage.onNext(nextPageParams.call(params));
                })
                .doOnError(errorSubject::onNext)
                .map(mapResponse)
                .onErrorResumeNext(Observable.empty())
                .doOnSubscribe(() -> loadSubject.onNext(true))
                .doOnSubscribe(() -> Timber.d("Fetching page " + params))
                .doOnTerminate(() -> loadSubject.onNext(false))
                .doOnTerminate(() -> Timber.d("Finished fetching page " + params));
    }

    public static <Data, Response, Params> Builder<Data, Response, Params> builder() {
        return new Builder<>();
    }

    public static class Builder<Data, Response, Params> {

        private Func1<Params, Observable<Response>> makeRequest;
        private Func1<Data, Boolean> stopWhen;
        private Func1<Response, Data> mapResponse;
        private Func1<Params, Params> nextPageParams;
        private Observable<Void> loadMore;
        private Observable<Params> startOver;
        private Func2<Data, Data, Data> concater;

        public Builder<Data, Response, Params> makeRequest(Func1<Params, Observable<Response>> makeRequest) {
            this.makeRequest = makeRequest;
            return this;
        }

        public Builder<Data, Response, Params> stopWhen(Func1<Data, Boolean> stopWhen) {
            this.stopWhen = stopWhen;
            return this;
        }

        public Builder<Data, Response, Params> mapResponse(Func1<Response, Data> mapResponse) {
            this.mapResponse = mapResponse;
            return this;
        }

        public Builder<Data, Response, Params> nextPageParams(Func1<Params, Params> nextPageParams) {
            this.nextPageParams = nextPageParams;
            return this;
        }

        public Builder<Data, Response, Params> loadMore(Observable<Void> loadMore) {
            this.loadMore = loadMore;
            return this;
        }

        public Builder<Data, Response, Params> startOver(Observable<Params> startOver) {
            this.startOver = startOver;
            return this;
        }

        public Builder<Data, Response, Params> concater(Func2<Data, Data, Data> concater) {
            this.concater = concater;
            return this;
        }

        public ApiPager<Data, Response, Params> build() {
            return new ApiPager<>(makeRequest, stopWhen, mapResponse, nextPageParams, loadMore, startOver, concater);
        }
    }
}