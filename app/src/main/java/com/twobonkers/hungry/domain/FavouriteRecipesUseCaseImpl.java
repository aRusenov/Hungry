package com.twobonkers.hungry.domain;

import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;
import com.twobonkers.hungry.IngredientModel;
import com.twobonkers.hungry.RecipeModel;
import com.twobonkers.hungry.StepModel;
import com.twobonkers.hungry.data.local.database.DatabaseManager;
import com.twobonkers.hungry.data.local.database.IngredientDbModel;
import com.twobonkers.hungry.data.local.database.RecipeDbModel;
import com.twobonkers.hungry.data.local.database.StepDbModel;
import com.twobonkers.hungry.data.local.preferences.LocalUserRepository;
import com.twobonkers.hungry.data.models.Ingredient;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.data.models.Step;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.util.UserUtils;

import rx.Observable;

public class FavouriteRecipesUseCaseImpl implements FavouriteRecipesUseCase {

    private RecipesService recipesService;
    private DatabaseManager databaseManager;
    private @Nullable String authToken;

    public FavouriteRecipesUseCaseImpl(RecipesService recipesService, DatabaseManager databaseManager,
                                       LocalUserRepository localUserRepository) {
        this.recipesService = recipesService;
        this.databaseManager = databaseManager;
        authToken = UserUtils.getBearerToken(localUserRepository.currentUser());
    }

    @Override
    public Observable<Boolean> favourite(Recipe recipe) {
        return recipesService.favouriteRecipe(recipe.id(), authToken)
                .flatMap(response -> saveRecipe(recipe, response.favourite()));
    }

    private Observable<Boolean> saveRecipe(Recipe recipe, boolean isFavourite) {
        return Observable.fromCallable(() -> {
            if (isFavourite) {
                insertRecipe(recipe);
                return true;
            } else {
                RecipeModel.Remove deleteStatement = new RecipeDbModel.Remove(databaseManager.getDatabase().getWritableDatabase());
                deleteStatement.bind(recipe.id());
                databaseManager.getDatabase().executeUpdateDelete(RecipeDbModel.TABLE_NAME, deleteStatement.program);
                return false;
            }
        });
    }

    private void insertRecipe(Recipe recipe) {
        BriteDatabase db = databaseManager.getDatabase();
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            // Add recipe
            RecipeModel.Add_new insertRecipe = new RecipeDbModel.Add_new(db.getWritableDatabase());
            insertRecipe.bind(recipe.id(), recipe.title(), recipe.previewImageUrl(), recipe.prepTime(), recipe.portions());
            long recipeId = db.executeInsert(RecipeDbModel.TABLE_NAME, insertRecipe.program);

            // Add ingredients
            IngredientDbModel.Add_new insertIngredient = new IngredientModel.Add_new(db.getWritableDatabase());
            for (Ingredient ingredient : recipe.ingredients()) {
                insertIngredient.bind(ingredient.name(), ingredient.quantity(), ingredient.measurement(), recipeId);
                db.executeInsert(IngredientDbModel.TABLE_NAME, insertIngredient.program);
            }

            // Add steps
            StepDbModel.Add_new insertStep = new StepModel.Add_new(db.getWritableDatabase());
            for (Step step : recipe.steps()) {
                insertStep.bind(step.order(), step.description(), recipeId);
                db.executeInsert(StepDbModel.TABLE_NAME, insertStep.program);
            }

            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }
}
