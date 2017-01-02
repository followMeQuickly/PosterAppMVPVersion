package com.example.chaseland.moviepostermvp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chaseland on 12/28/16.
 */

public class PosterDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Posters.db";
    public PosterDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        final String SQL_CREATE_POSTER_TABLE = "CREATE TABLE "
//                + MoviePosterContract.PosterEntry.TABLE_NAME
//                + " (" + MoviePosterContract.PosterEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
//                MoviePosterContract.PosterEntry.COLUMN_POSTER_PATH + " TEXT , " +
//                MoviePosterContract.PosterEntry.COLUMN_MOVIE_TITLE + " TEXT , " +
//                MoviePosterContract.PosterEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT , "+
//                MoviePosterContract.PosterEntry.COLUMN_VOTE + " TEXT , " +
//
//                MoviePosterContract.PosterEntry.COLUMN_RELEASE_DATE + " TEXT, " +
//                " UNIQUE ("+ MoviePosterContract.PosterEntry.COLUMN_MOVIE_ID +") ON CONFLICT REPLACE);";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
