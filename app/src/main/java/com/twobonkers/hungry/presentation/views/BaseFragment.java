package com.twobonkers.hungry.presentation.views;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.twobonkers.hungry.presentation.utils.LifecycleRetainer;

import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class BaseFragment<ViewModel extends BaseViewModel> extends RxFragment {

    private final BehaviorSubject<FragmentEvent> lifecycle = BehaviorSubject.create();
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    protected ViewModel viewModel;

    @CallSuper
    @Override
    public void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate %s", this.toString());

        viewModel = LifecycleRetainer.getInstance().get(savedInstanceState);
        lifecycle.onNext(FragmentEvent.CREATE);
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart %s", this.toString());
        lifecycle.onNext(FragmentEvent.START);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume %s", this.toString());
        lifecycle.onNext(FragmentEvent.RESUME);

        if (viewModel != null) {
            viewModel.onResume(this);
        }
    }

    @CallSuper
    @Override
    public void onPause() {
        lifecycle.onNext(FragmentEvent.PAUSE);
        super.onPause();
        Timber.d("onPause %s", this.toString());

        if (viewModel != null) {
            viewModel.onPause();
        }
    }

    @CallSuper
    @Override
    public void onStop() {
        lifecycle.onNext(FragmentEvent.STOP);
        super.onStop();
        Timber.d("onStop %s", this.toString());
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            LifecycleRetainer.getInstance().save(viewModel, outState);
        }
    }

    @CallSuper
    @Override
    public void onDestroy() {
        lifecycle.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        Timber.d("onDestroy %s", this.toString());

        subscriptions.clear();

        if (isRemoving() && viewModel != null) {
            LifecycleRetainer.getInstance().remove(viewModel);
        }

        viewModel = null;
    }
}
