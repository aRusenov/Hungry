package com.twobonkers.hungry.data.local.database;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;
import com.twobonkers.hungry.RecipeModel;

@AutoValue
public abstract class RecipeDbModel implements RecipeModel {

    public static final Factory<RecipeModel> FACTORY = new Factory<>(new Creator<RecipeModel>() {
        @Override
        public RecipeModel create(long _id, @NonNull String title, @NonNull String image_url, long prep_time, long portions) {
            return new AutoValue_RecipeDbModel(_id, title, image_url, prep_time, portions);
        }
    });

    public static final Mapper<RecipeModel> SELECT_ALL_MAPPER = FACTORY.get_allMapper();

    public static final RowMapper<GetIngredients> GET_INGREDIENTS_MAPPER = FACTORY.get_ingredientsMapper(AutoValue_RecipeDbModel_GetIngredients::new);

    public static final RowMapper<GetSteps> GET_STEPS_MAPPER = FACTORY.get_stepsMapper(AutoValue_RecipeDbModel_GetSteps::new);

    @AutoValue
    public static abstract class GetIngredients implements RecipeModel.Get_ingredientsModel {
    }

    @AutoValue
    public static abstract class GetSteps implements RecipeModel.Get_stepsModel {
    }
}
