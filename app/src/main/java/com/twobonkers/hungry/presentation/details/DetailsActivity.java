package com.twobonkers.hungry.presentation.details;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.presentation.IntentKeys;
import com.twobonkers.hungry.presentation.dagger.DetailsModule;
import com.twobonkers.hungry.presentation.utils.FastAdapterMapper;
import com.twobonkers.hungry.presentation.views.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static android.view.View.GONE;

public class DetailsActivity extends BaseActivity<DetailsViewModel> {

    private FastItemAdapter<IngredientItem> ingredientsAdapter;
    private FastItemAdapter<StepItem> stepsAdapter;

    protected @BindView(R.id.coordinator) CoordinatorLayout coordinator;
    protected @BindView(R.id.toolbar) Toolbar toolbar;
    protected @BindView(R.id.iv_preview) ImageView ivPreview;
    protected @BindView(R.id.rv_ingredients) RecyclerView rvIngredients;
    protected @BindView(R.id.rv_steps) RecyclerView rvSteps;
    protected @BindView(R.id.fab_progress) FABProgressCircle fabProgress;
    protected @BindView(R.id.fab) FloatingActionButton fab;

    protected @BindView(R.id.tv_favourite_count) TextView tvFavCount;
    protected @BindView(R.id.tv_portions) TextView tvPortions;
    protected @BindView(R.id.tv_prep) TextView tvPrepTime;

    @Override
    protected void initializeViewModel() {
        application().appComponent()
                .plus(new DetailsModule())
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        toolbar.setNavigationOnClickListener(v -> finish());

        ingredientsAdapter = new FastItemAdapter<>();
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        stepsAdapter = new FastItemAdapter<>();
        rvSteps.setAdapter(stepsAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Recipe recipe = getIntent().getParcelableExtra(IntentKeys.RECIPE);
        setRecipe(recipe);

        if (getIntent().getBooleanExtra(IntentKeys.OFFLINE_MODE, false)) {
            tvFavCount.setVisibility(GONE);
            fabProgress.setVisibility(GONE);
        }

        viewModel.outputs.recipe()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRecipe);

        viewModel.outputs.loading()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayLoading);

        viewModel.outputs.error()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayFavouriteError);
    }

    @OnClick(R.id.fab_progress) void onFabClicked() {
        viewModel.inputs.clickFavourite();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.clear(ivPreview);
    }

    private void setRecipe(Recipe recipe) {
        getSupportActionBar().setTitle(recipe.title());
        tvPortions.setText(getString(R.string.recipe_portions, recipe.portions()));
        tvPrepTime.setText(getString(R.string.recipe_prep_time, recipe.prepTime()));
        tvFavCount.setText(getString(R.string.recipe_favourite_count, recipe.favouriteCount()));

        ingredientsAdapter.add(FastAdapterMapper.toIngredientItems(recipe.ingredients()));
        stepsAdapter.add(FastAdapterMapper.toStepItems(recipe.steps()));

        setFabDrawable(recipe.favourited());
        Glide.with(this).load(recipe.previewImageUrl()).into(ivPreview);
    }

    private void displayLoading(boolean isLoading) {
        if (isLoading) {
            fabProgress.show();
        } else {
            fabProgress.hide();
        }
    }

    private void displayFavouriteError(Throwable error) {
        Timber.e(error);
        showSnackbar(getString(R.string.error_favourite_post));
    }

    private void updateRecipe(Recipe newRecipe) {
        showSnackbar(newRecipe.favourited() ?
                getString(R.string.success_recipe_added_favourites) :
                getString(R.string.success_recipe_removed_favourites));

        setFabDrawable(newRecipe.favourited());

        tvFavCount.setText(getString(R.string.recipe_favourite_count, newRecipe.favouriteCount()));
    }

    private void setFabDrawable(boolean favourite) {
        fab.setImageResource(favourite ?
                R.drawable.ic_favorite_white_36dp :
                R.drawable.ic_favorite_border_white_36dp);
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show();
    }
}
