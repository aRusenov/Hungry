package com.twobonkers.hungry.presentation.dagger.app;

import com.google.gson.GsonBuilder;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.lib.autogson.AutoValueAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides @Singleton
    public RecipesService provideRecipesService(Retrofit retrofit) {
        return retrofit.create(RecipesService.class);
    }

    @Provides @Singleton
    public Retrofit provideRetrofit(CallAdapter.Factory callAdapterFactory, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides @Singleton
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides @Singleton
    public Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueAdapterFactory())
                .create());
    }
}
