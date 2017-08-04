package com.example.chaseland.moviepostermvp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chaseland on 12/28/16.
 */

public class PosterDBHelper extends SQLiteOpenHelper {

    private final String  SQL_CREATE_POSTER_TABLE = "CREATE TABLE "
            + PosterPersistenceContract.PosterEntry.TABLE_NAME
            + " (" + PosterPersistenceContract.PosterEntry.ID_COLUMN + " INTEGER PRIMARY KEY, " +
            PosterPersistenceContract.PosterEntry.IMAGE_PATH_COLUMN + " TEXT , " +
            PosterPersistenceContract.PosterEntry.TITLE_COLUMN + " TEXT , " +
            PosterPersistenceContract.PosterEntry.DESCRIPTION_COLUMN + " TEXT , "+
            PosterPersistenceContract.PosterEntry.VOTE_COLUMN + " TEXT , " +

            PosterPersistenceContract.PosterEntry.RELEASE_DATE_COLUMN + " TEXT, " +
            PosterPersistenceContract.PosterEntry.BACKDROP_PATH + " TEXT, " +
            " UNIQUE ("+ PosterPersistenceContract.PosterEntry.ID_COLUMN +") ON CONFLICT REPLACE);";

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Posters.db";

    public PosterDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + PosterPersistenceContract.PosterEntry.TABLE_NAME);
        db.execSQL(SQL_CREATE_POSTER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            onCreate(db);

        }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion)
        {

            onCreate(db);
        }
    }
}
