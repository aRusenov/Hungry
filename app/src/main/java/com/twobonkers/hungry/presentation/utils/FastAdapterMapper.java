package com.twobonkers.hungry.presentation.utils;

import com.twobonkers.hungry.data.models.Ingredient;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.data.models.Step;
import com.twobonkers.hungry.presentation.details.IngredientItem;
import com.twobonkers.hungry.presentation.details.StepItem;
import com.twobonkers.hungry.presentation.feed.FeedItem;

import java.util.ArrayList;
import java.util.List;

public class FastAdapterMapper {

    public static List<FeedItem> toFeedItems(List<Recipe> recipes) {
        List<FeedItem> feedItems = new ArrayList<>(recipes.size());
        for (int i = 0; i < recipes.size(); i++) {
            feedItems.add(new FeedItem(recipes.get(i)));
        }

        return feedItems;
    }

    public static List<StepItem> toStepItems(List<Step> steps) {
        List<StepItem> stepItems = new ArrayList<>(steps.size());
        for (int i = 0; i < steps.size(); i++) {
            stepItems.add(new StepItem(steps.get(i)));
        }

        return stepItems;
    }

    public static List<IngredientItem> toIngredientItems(List<Ingredient> ingredients) {
        List<IngredientItem> ingredientItems = new ArrayList<>(ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientItems.add(new IngredientItem(ingredients.get(i)));
        }

        return ingredientItems;
    }
}
