package com.example.chaseland.moviepostermvp.data.source;

import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;

import java.util.List;

/**
 * Created by chaseland on 12/28/16.
 */

public interface PosterSource {

    interface LoadPostersCallback {

        void onPostersLoaded(List<Poster> posters);

        void onDataNotAvailable();

    }
    interface GetPosterCallback {
        void onPosterLoaded(Poster poster);

        void onDataNotAvailable();

    }

    void GetPosters(@NonNull LoadPostersCallback callback);

    void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback);

    void savePoster(@NonNull Poster poster);

    void refreshPosters();

    void deleteAllPosters();

    void deletePoster(String posterId);


}
