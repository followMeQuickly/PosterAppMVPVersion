package com.example.chaseland.moviepostermvp.data.source;

import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;

/**
 * Created by chaseland on 12/28/16.
 */

public class PosterRepository implements PosterSource {
    @Override
    public void GetPosters(@NonNull LoadPostersCallback callback) {

    }

    @Override
    public void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback) {

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

    @Override
    public void deletePoster(String posterId) {

    }
}
