package com.example.chaseland.moviepostermvp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Posters;
import com.example.chaseland.moviepostermvp.data.Reviews;
import com.example.chaseland.moviepostermvp.data.Trailer;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.data.source.local.PosterPersistenceContract.PosterEntry;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by chaseland on 12/28/16.
 */

public class LocalPosterDataSource implements PosterSource {

    private PosterDBHelper posterDBHelper;

    private static LocalPosterDataSource INSTANCE = null;

    private LocalPosterDataSource(Context context){
        posterDBHelper = new PosterDBHelper(context);

    }

    public static LocalPosterDataSource getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new LocalPosterDataSource(context);
        }
        return INSTANCE;



    }

    @Override
    public void refreshTrailers() {

    }

    @Override
    public void getPosters(@NonNull LoadPostersCallback callback, PosterFilterType filtering) {

        List<Poster> posters = new ArrayList<>();
        SQLiteDatabase db = posterDBHelper.getReadableDatabase();
        String[] projections = {
               PosterEntry.ID_COLUMN,
               PosterEntry.TITLE_COLUMN,
               PosterEntry.DESCRIPTION_COLUMN,
               PosterEntry.VOTE_COLUMN,
               PosterEntry.RELEASE_DATE_COLUMN,
               PosterEntry.FAVORITE_COLUMN


        };

        Cursor posterCursor = db.query(PosterEntry.TABLE_NAME, projections, null, null, null, null, null);

        if(posterCursor != null && posterCursor.getCount() > 0) {
            while(posterCursor.moveToNext()){
                String posterId = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.ID_COLUMN));
                String description = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.DESCRIPTION_COLUMN));
                String title = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.TITLE_COLUMN));
                int vote = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.VOTE_COLUMN));
                String releaseDate = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.RELEASE_DATE_COLUMN));
                String imagePath = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.IMAGE_PATH_COLUMN));
                int favoriteRep = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.FAVORITE_COLUMN));
                boolean isFavorited = (favoriteRep == 0);


            }
        }
        if(posterCursor != null){
            posterCursor.close();
        }
        db.close();
        if(posters.isEmpty()){
            callback.onDataNotAvailable();
        }
        else{
            callback.onPostersLoaded(posters);
        }

    }

    @Override
    public void deletePoster(String posterId) {

    }

    @Override
    public Observable<Trailer> getTrailer(@NonNull String posterId) {
        return null;
    }

    @Override
    public Observable<Posters> getPosters(PosterFilterType filtering) {
        return null;
    }

    @Override
    public Observable<Reviews> getReviews(String posterId) {
        return null;
    }

    @Override
    public void refreshReviews() {

    }

    @Override
    public void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback) {

        SQLiteDatabase db = posterDBHelper.getReadableDatabase();
        String[] projections = {
                PosterEntry.ID_COLUMN,
                PosterEntry.TITLE_COLUMN,
                PosterEntry.DESCRIPTION_COLUMN,
                PosterEntry.VOTE_COLUMN,
                PosterEntry.RELEASE_DATE_COLUMN,
                PosterEntry.IMAGE_PATH_COLUMN,
                PosterEntry.BACKDROP_PATH



        };

        String selection  = PosterEntry.ID_COLUMN + " LIKE ?";
        String[] selectionArgs = { posterId };
        Cursor posterCursor = db.query(PosterEntry.TABLE_NAME, projections, selection, selectionArgs, null, null, null);
        Poster poster = null;
        if(posterCursor != null && posterCursor.getCount() > 0){
            posterCursor.moveToFirst();

            String description = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.DESCRIPTION_COLUMN));
            String title = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.TITLE_COLUMN));
            int vote = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.VOTE_COLUMN));
            String releaseDate = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.RELEASE_DATE_COLUMN));
            String imagePath = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.IMAGE_PATH_COLUMN));
            String backdropPath = posterCursor.getString(posterCursor.getColumnIndex(PosterEntry.BACKDROP_PATH));
            poster = new Poster();
            poster.setPosterPath(imagePath);
            poster.setTitle(title);
            poster.setVoteCount(vote);
            poster.setReleaseDate(releaseDate);
            poster.setOverview(description);
            poster.setBackdropPath(backdropPath);

        }
        if(posterCursor != null){
            posterCursor.close();
        }
        if(poster == null){
            callback.onDataNotAvailable();
        }
        else{
            callback.onPosterLoaded(poster);
        }

    }

    @Override
    public void savePoster(@NonNull Poster poster) {
        SQLiteDatabase db = posterDBHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(PosterEntry.TITLE_COLUMN, poster.getTitle());
        values.put(PosterEntry.DESCRIPTION_COLUMN, poster.getOverview());
        values.put(PosterEntry.ID_COLUMN, poster.getId());
        values.put(PosterEntry.VOTE_COLUMN, poster.getVoteCount());
        values.put(PosterEntry.RELEASE_DATE_COLUMN, poster.getReleaseDate());
        values.put(PosterEntry.BACKDROP_PATH, poster.getBackdropPath());
        values.put(PosterEntry.IMAGE_PATH_COLUMN, poster.getPosterPath());

        db.insert(PosterEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void refreshPosters() {

    }

    @Override
    public void deleteAllPosters() {
        SQLiteDatabase db = posterDBHelper.getWritableDatabase();
        //
        db.delete(PosterEntry.TABLE_NAME, null, null);
        db.close();

    }
}
