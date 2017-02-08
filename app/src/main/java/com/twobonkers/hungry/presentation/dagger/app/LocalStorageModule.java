package com.twobonkers.hungry.presentation.dagger.app;

import android.app.Application;

import com.twobonkers.hungry.data.local.database.DatabaseManager;
import com.twobonkers.hungry.data.local.database.SqliteHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalStorageModule {

    @Provides @Singleton
    public DatabaseManager provideDatabaseManager(Application application) {
        return new DatabaseManager(new SqliteHelper(application));
    }
}
