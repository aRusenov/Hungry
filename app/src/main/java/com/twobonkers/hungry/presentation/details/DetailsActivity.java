package com.twobonkers.hungry.presentation.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.presentation.views.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity<DetailsViewModel> {

    private static final String EXTRA_RECIPE = "recipe";

    private FastItemAdapter<IngredientItem> ingredientsAdapter;
    private FastItemAdapter<StepItem> stepsAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.iv_preview) ImageView ivPreview;
    @BindView(R.id.rv_ingredients) RecyclerView rvIngredients;
    @BindView(R.id.rv_steps) RecyclerView rvSteps;

    public static Intent prepareIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(recipe.title());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        toolbar.setNavigationOnClickListener(v -> finish());

        ingredientsAdapter = new FastItemAdapter<>();
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        stepsAdapter = new FastItemAdapter<>();
        rvSteps.setAdapter(stepsAdapter);
        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Glide.with(this).load(recipe.previewImageUrl()).into(ivPreview);

        if (savedInstanceState == null) {
            viewModel = new DetailsViewModel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.clear(ivPreview);
    }
}
