package com.twobonkers.hungry.presentation.views;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.subjects.PublishSubject;
import timber.log.Timber;


public class ActivityViewModel<View extends BaseActivity> {

    private final PublishSubject<View> viewChange = PublishSubject.create();
    private final Observable<View> view = viewChange.filter(v -> v != null);
    private final PublishSubject<Intent> intent = PublishSubject.create();

    protected Observable<Intent> intent() {
        return intent;
    }

    @CallSuper
    protected void onCreate() {
        Timber.d("onCreate %s", this.toString());
        dropView();
    }

    public void intent(Intent intent) {
        this.intent.onNext(intent);
    }

    @CallSuper
    protected void onResume(final @NonNull View view) {
        Timber.d("onResume %s", this.toString());
        onTakeView(view);
    }

    @CallSuper
    protected void onPause() {
        Timber.d("onPause %s", this.toString());
        dropView();
    }

    @CallSuper
    protected void onDestroy() {
        Timber.d("onDestroy %s", this.toString());
        viewChange.onCompleted();
    }

    private void onTakeView(final @NonNull View view) {
        Timber.d("onTakeView %s %s", this.toString(), view.toString());
        viewChange.onNext(view);
    }

    private void dropView() {
        Timber.d("dropView %s", this.toString());
        viewChange.onNext(null);
    }

    /**
     * By composing this transformer with an observable you guarantee that every observable in your view model
     * will be properly completed when the view model completes.
     * <p>
     * It is required that *every* observable in a view model do `.compose(bindToLifecycle())` before calling
     * `subscribe`.
     */
    public @NonNull <T> Observable.Transformer<T, T> bindToLifecycle() {
        return source -> source.takeUntil(
                view.switchMap(v -> v.lifecycle().map(e -> Pair.create(v, e)))
                        .filter(ve -> isBeingRemoved(ve.first, ve.second)));
    }

    private boolean isBeingRemoved(View view, ActivityEvent event) {
        return event == ActivityEvent.DESTROY.DESTROY && view.isFinishing();
    }
}
