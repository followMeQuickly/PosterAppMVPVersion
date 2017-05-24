package com.example.chaseland.moviepostermvp.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by chaseland on 12/28/16.
 */

public final class PosterPersistenceContract {
    public static abstract class PosterEntry implements BaseColumns {
        public static final String TABLE_NAME = "Posters";
        public static final String ID_COLUMN = "id";
        public static final String IMAGE_PATH_COLUMN = "imagePath";
        public static final String TITLE_COLUMN = "title";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String VOTE_COLUMN = "vote";
        public static final String RELEASE_DATE_COLUMN = "releaseDate";
        public static final String FAVORITE_COLUMN = "favorited";



    }

    public static abstract class ReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "Posters";
        public static final String ID_COLUMN = "id";
        public static final String IMAGE_PATH_COLUMN = "imagePath";
        public static final String TRAILER_IMAGE_PATH_COLUMN = "trailerImagePath";
    }
}
