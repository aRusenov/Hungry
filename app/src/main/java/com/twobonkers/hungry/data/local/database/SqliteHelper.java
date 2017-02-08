package com.twobonkers.hungry.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.twobonkers.hungry.db";
    public static final int DB_VERSION = 1;

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeDbModel.CREATE_TABLE);
        db.execSQL(StepDbModel.CREATE_TABLE);
        db.execSQL(IngredientDbModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Enable cascade delete
        db.execSQL("PRAGMA foreign_keys=ON");
    }
}
