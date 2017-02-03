package com.twobonkers.hungry;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.twobonkers.hungry.data.RecipesService;
import com.twobonkers.hungry.domain.lib.autogson.AutoValueAdapterFactory;
import com.twobonkers.hungry.presentation.utils.ConfigLoader;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class HApplication extends Application {

    private Retrofit retrofit;
    private RecipesService recipesService;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(ConfigLoader.getConfigValue(this, "api_url"))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapterFactory(new AutoValueAdapterFactory())
                        .create()))
                .build();

        recipesService = retrofit.create(RecipesService.class);
    }

    public RecipesService getRecipesService() {
        return recipesService;
    }
}
