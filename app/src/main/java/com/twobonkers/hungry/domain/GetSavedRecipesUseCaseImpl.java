package com.twobonkers.hungry.domain;

import android.database.Cursor;

import com.twobonkers.hungry.RecipeModel;
import com.twobonkers.hungry.data.local.database.DatabaseManager;
import com.twobonkers.hungry.data.local.database.RecipeDbModel;
import com.twobonkers.hungry.data.models.Ingredient;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.data.models.Step;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Observable;

public class GetSavedRecipesUseCaseImpl implements GetSavedRecipesUseCase {

    private DatabaseManager databaseManager;

    public GetSavedRecipesUseCaseImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Observable<List<Recipe>> getSavedRecipes() {
        return databaseManager.getDatabase().createQuery(RecipeDbModel.TABLE_NAME, RecipeDbModel.GET_ALL)
                .map(query -> {
                    LinkedHashMap<Long, Recipe> recipes = getRecipes(query.run());
                    loadIngredients(recipes);
                    loadSteps(recipes);

                    return new ArrayList<>(recipes.values());
                });
    }

    private void loadSteps(LinkedHashMap<Long, Recipe> recipes) {
        Cursor cursor = databaseManager.getDatabase().query(RecipeDbModel.GET_STEPS);
        try {
            while (cursor.moveToNext()) {
                RecipeDbModel.GetSteps s = RecipeDbModel.GET_STEPS_MAPPER.map(cursor);
                Step step = Step.builder()
                        .description(s.description())
                        .order((int) s.order())
                        .build();

                recipes.get(s.recipe_id()).steps().add(step);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void loadIngredients(LinkedHashMap<Long, Recipe> recipes) {
        Cursor cursor = databaseManager.getDatabase().query(RecipeDbModel.GET_INGREDIENTS);
        try {
            while (cursor.moveToNext()) {
                RecipeDbModel.GetIngredients i = RecipeDbModel.GET_INGREDIENTS_MAPPER.map(cursor);
                Ingredient ingredient = Ingredient.builder()
                        .name(i.name())
                        .measurement((int) i.measurement())
                        .quantity((int) i.quantity())
                        .build();

                recipes.get(i.recipe_id()).ingredients().add(ingredient);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private LinkedHashMap<Long, Recipe> getRecipes(Cursor cursor) {
        try {
            LinkedHashMap<Long, Recipe> recipes = new LinkedHashMap<>();
            while (cursor.moveToNext()) {
                RecipeModel r = RecipeDbModel.SELECT_ALL_MAPPER.map(cursor);
                recipes.put(r._id(), Recipe.builder()
                        .id(r._id())
                        .title(r.title())
                        .previewImageUrl(r.image_url())
                        .favouriteCount(0)
                        .favourited(true)
                        .prepTime((int) r.prep_time())
                        .portions((int) r.portions())
                        .ingredients(new ArrayList<>())
                        .steps(new ArrayList<>())
                        .build());
            }

            return recipes;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
