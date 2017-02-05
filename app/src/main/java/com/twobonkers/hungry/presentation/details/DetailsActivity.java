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
import com.twobonkers.hungry.HApplication;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.domain.FavouriteRecipesUseCaseImpl;
import com.twobonkers.hungry.presentation.IntentKeys;
import com.twobonkers.hungry.presentation.utils.FastAdapterMapper;
import com.twobonkers.hungry.presentation.views.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

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
    protected DetailsViewModel createViewModel() {
        HApplication app = (HApplication) getApplication();
        return new DetailsViewModel(
                new FavouriteRecipesUseCaseImpl(app.getRecipesService()),
                app.getRecipeChangeBus());
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

        viewModel.outputs.recipe()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateFab);

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
        tvPortions.setText(String.valueOf(recipe.portions()));
        tvPrepTime.setText(String.valueOf(recipe.prepTime()));
        tvPrepTime.append(getString(R.string.suffix_minutes));
        tvFavCount.setText(String.valueOf(recipe.favouriteCount()));
        tvFavCount.append(getString(R.string.suffix_favourite_count));

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
        showSnackbar(getString(R.string.error_favourite_post));
    }

    private void updateFab(Recipe newRecipe) {
        showSnackbar(newRecipe.favourited() ?
                getString(R.string.success_recipe_added_favourites) :
                getString(R.string.success_recipe_removed_favourites));

        setFabDrawable(newRecipe.favourited());
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
