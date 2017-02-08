package com.twobonkers.hungry;

import android.app.Application;

import com.twobonkers.hungry.presentation.dagger.app.AppComponent;
import com.twobonkers.hungry.presentation.dagger.app.BaseModule;
import com.twobonkers.hungry.presentation.dagger.app.DaggerAppComponent;
import com.twobonkers.hungry.presentation.dagger.app.NetModule;
import com.twobonkers.hungry.presentation.utils.ConfigLoader;

import timber.log.Timber;

public class HApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        appComponent = DaggerAppComponent.builder()
                .baseModule(new BaseModule(this))
                .netModule(new NetModule(ConfigLoader.getConfigValue(this, "api_url")))
                .build();
    }

    public AppComponent appComponent() {
        return appComponent;
    }
}
