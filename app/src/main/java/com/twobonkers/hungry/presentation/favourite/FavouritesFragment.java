package com.twobonkers.hungry.presentation.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.presentation.IntentKeys;
import com.twobonkers.hungry.presentation.dagger.FavouritesModule;
import com.twobonkers.hungry.presentation.details.DetailsActivity;
import com.twobonkers.hungry.presentation.utils.FastAdapterMapper;
import com.twobonkers.hungry.presentation.views.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class FavouritesFragment extends BaseFragment<FavouritesViewModel> {

    private FastItemAdapter<FavouriteRecipeItem> adapter;

    protected @BindView(R.id.rv_recipes) RecyclerView rvRecipes;
    protected @BindView(R.id.tv_no_favourites) TextView tvNoFavourites;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    protected void createViewModel() {
        application().appComponent()
                .plus(new FavouritesModule())
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);

        adapter = new FastItemAdapter<>();
        adapter.withOnClickListener((v, adapter1, item, position) -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra(IntentKeys.RECIPE, item.getRecipe());
            intent.putExtra(IntentKeys.OFFLINE_MODE, true);
            startActivity(intent);

            return true;
        });

        rvRecipes.setAdapter(adapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.outputs.savedRecipes()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .map(FastAdapterMapper::toFavouriteItems)
                .subscribe(recipes -> {
                    adapter.set(recipes);
                    tvNoFavourites.setVisibility(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                });
    }
}
