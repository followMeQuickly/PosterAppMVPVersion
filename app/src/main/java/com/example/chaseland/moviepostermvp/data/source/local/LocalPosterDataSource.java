package com.example.chaseland.moviepostermvp.data.source.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.data.source.local.PosterPersistenceContract.PosterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaseland on 12/28/16.
 */

public class LocalPosterDataSource implements PosterSource {

    private PosterDBHelper posterDBHelper;

    private static LocalPosterDataSource INSTANCE;
    @Override
    public void GetPosters(@NonNull LoadPostersCallback callback) {

        List<Poster> posters = new ArrayList<>();
        SQLiteDatabase db = posterDBHelper.getReadableDatabase();
        String[] projections = {
               PosterEntry.ID_COLUMN,
               PosterEntry.TITLE_COLUMN,
               PosterEntry.DESCRIPTION_COLUMN,
               PosterEntry.VOTE_COLUMN,
               PosterEntry.RELEASEDATE_COLUMN,
               PosterEntry.FAVORITE_COLUMN


        };

        Cursor posterCursor = db.query(PosterEntry.TABLE_NAME, projections, null, null, null, null, null);

        if(posterCursor != null && posterCursor.getCount() > 0) {
            while(posterCursor.moveToNext()){
                String posterId = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.ID_COLUMN));
                String description = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.DESCRIPTION_COLUMN));
                String title = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.TITLE_COLUMN));
                int vote = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.VOTE_COLUMN));
                String releaseDate = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.RELEASEDATE_COLUMN));
                String imagePath = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.IMAGE_PATH_COLUMN));
                int favoriteRep = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.FAVORITE_COLUMN));
                boolean isFavorited = (favoriteRep == 0) ? true : false;
                Poster poster = new Poster(posterId, title, description, isFavorited, vote, releaseDate, imagePath);
                posters.add(poster);


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
    public void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback) {

        SQLiteDatabase db = posterDBHelper.getReadableDatabase();
        String[] projections = {
                PosterEntry.ID_COLUMN,
                PosterEntry.TITLE_COLUMN,
                PosterEntry.DESCRIPTION_COLUMN,
                PosterEntry.VOTE_COLUMN,
                PosterEntry.RELEASEDATE_COLUMN,
                PosterEntry.FAVORITE_COLUMN


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
            String releaseDate = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.RELEASEDATE_COLUMN));
            String imagePath = posterCursor.getString(posterCursor.getColumnIndexOrThrow(PosterEntry.IMAGE_PATH_COLUMN));
            int favoriteRep = posterCursor.getInt(posterCursor.getColumnIndexOrThrow(PosterEntry.FAVORITE_COLUMN));
            boolean isFavorited = (favoriteRep == 0) ? true : false;
            poster = new Poster(posterId, title, description, isFavorited, vote, releaseDate, imagePath);

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

    }

    @Override
    public void refreshPosters() {

    }

    @Override
    public void deleteAllPosters() {

    }
}
