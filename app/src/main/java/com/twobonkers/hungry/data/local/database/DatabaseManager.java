package com.twobonkers.hungry.data.local.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

public class DatabaseManager {

    private SQLiteOpenHelper openHelper;
    private BriteDatabase briteDatabase;

    public DatabaseManager(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;

        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        briteDatabase = sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    public BriteDatabase getDatabase() {
        return briteDatabase;
    }
}
