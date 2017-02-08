package com.twobonkers.hungry.presentation.dagger.app;

import android.app.Application;

import com.twobonkers.hungry.data.local.preferences.LocalUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    private Application application;

    public BaseModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides @Singleton
    public LocalUserRepository provideUserRepository() {
        return new LocalUserRepository(application);
    }
}
