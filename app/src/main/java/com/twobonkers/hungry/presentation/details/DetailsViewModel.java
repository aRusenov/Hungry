package com.twobonkers.hungry.presentation.details;

import com.twobonkers.hungry.presentation.views.ActivityViewModel;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

public class DetailsViewModel extends ActivityViewModel<DetailsActivity> {

    private Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS);

    private Subscription sub;

    public DetailsViewModel() {
        sub = obs.compose(bindToLifecycle())
                .subscribe(l -> Timber.d("%d", l),
                        Timber::e,
                        () -> Timber.d("Completed"));
    }
}
