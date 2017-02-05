package com.twobonkers.hungry.presentation.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.twobonkers.hungry.HApplication;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.presentation.IntentKeys;
import com.twobonkers.hungry.presentation.details.DetailsActivity;
import com.twobonkers.hungry.presentation.utils.FastAdapterMapper;
import com.twobonkers.hungry.presentation.views.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.twobonkers.hungry.domain.lib.rx.Transformers.observeForUI;

public class FeedFragment extends BaseFragment<FeedViewModel> {

    private FastItemAdapter<FeedItem> adapter;
    private FooterAdapter<AbstractItem> footerAdapter;

    protected @BindView(R.id.rv_feed) RecyclerView rvFeed;
    protected @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        adapter = new FastItemAdapter<>();
        footerAdapter = new FooterAdapter<>();
        EndlessRecyclerOnScrollListener endlessScrollListener = new EndlessRecyclerOnScrollListener(footerAdapter) {
            @Override
            public void onLoadMore(int currentPage) {
                viewModel.inputs.nextPage();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.inputs.refresh();
            endlessScrollListener.resetPageCount();
        });

        adapter.withOnClickListener((v, adapter1, item, position) -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra(IntentKeys.RECIPE, item.getRecipe());
            startActivity(intent);
            return true;
        });

        rvFeed.setAdapter(footerAdapter.wrap(adapter));
        rvFeed.addOnScrollListener(endlessScrollListener);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HApplication app = (HApplication) getActivity().getApplication();
        if (viewModel == null) {
            viewModel = new FeedViewModel(app.getRecipesService(), app.getRecipeChangeBus());
        }

        viewModel.outputs.recipes()
                .compose(bindToLifecycle())
                .compose(observeForUI())
                .map(FastAdapterMapper::toFeedItems)
                .subscribe(recipes -> adapter.set(recipes));

        viewModel.outputs.showLoading()
                .mergeWith(viewModel.outputs.onLastPage().map(__ -> false))
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loading -> {
                    if (loading) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                });

        viewModel.outputs.showRetry()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(retry -> {
                    if (retry) {
                        showRetry();
                    } else {
                        hideRetry();
                    }
                });

        viewModel.outputs.error()
                .compose(bindToLifecycle())
                .compose(observeForUI())
                .subscribe(error -> {
                    showRetry();
                    Timber.e(error);
                });
    }

    private void showLoading() {
        if (footerAdapter.getAdapterPosition(LoadingItem.IDENTIFIER) == -1) {
            footerAdapter.add(new LoadingItem()
                    .withIdentifier(LoadingItem.IDENTIFIER)
                    .withEnabled(true));
        }
    }

    private void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        removeItemFromFooter(LoadingItem.IDENTIFIER);
    }

    private void showRetry() {
        if (footerAdapter.getAdapterPosition(RetryItem.IDENTIFIER) == -1) {
            footerAdapter.add(new RetryItem()
                    .withIdentifier(RetryItem.IDENTIFIER)
                    .withEnabled(true)
                    .withOnItemClickListener((v, adapter1, item, position) -> {
                        viewModel.clickRetry();
                        return true;
                    }));
        }
    }

    private void hideRetry() {
        removeItemFromFooter(RetryItem.IDENTIFIER);
    }

    private void removeItemFromFooter(int identifier) {
        int relativePos = footerAdapter.getAdapterPosition(identifier);
        if (relativePos != -1) {
            int globalPos = footerAdapter.getGlobalPosition(relativePos);
            if (globalPos != -1) {
                footerAdapter.remove(globalPos);
            }
        }
    }
}
