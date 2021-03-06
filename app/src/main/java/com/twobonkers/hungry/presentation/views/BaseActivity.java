package com.twobonkers.hungry.presentation.views;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.twobonkers.hungry.HApplication;
import com.twobonkers.hungry.presentation.utils.LifecycleRetainer;

import javax.inject.Inject;

public abstract class BaseActivity<ViewModel extends ActivityViewModel> extends RxAppCompatActivity {

    protected @Inject ViewModel viewModel;

    /**
     * Called in {@link #onCreate(Bundle)} if the view model is null to assign a value to {@link BaseFragment#viewModel}.
     * The assigned value is later cached in {@link #onSaveInstanceState(Bundle)} and re-used in case of
     * activity recreation.
     */
    protected abstract void initializeViewModel();

    protected HApplication application() {
        return (HApplication) getApplication();
    }

    @CallSuper
    @Override
    public void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = LifecycleRetainer.getInstance().get(savedInstanceState);
        if (viewModel == null) {
            initializeViewModel();
        }

        viewModel.intent(getIntent());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.onResume(this); // TODO: What happens if type mismatch?
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewModel != null) {
            viewModel.onPause();
        }
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
        super.onDestroy();
        if (isFinishing() && viewModel != null) {
            LifecycleRetainer.getInstance().remove(viewModel);
            viewModel.onDestroy();
            viewModel = null;
        }
    }
}
