package com.example.chaseland.moviepostermvp.data.source;

import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import java.util.List;

import rx.Observable;

/**
 * Created by chaseland on 12/28/16.
 */

public interface PosterSource {

    void refreshTrailers();

    interface LoadPostersCallback {

        void onPostersLoaded(List<Poster> posters);

        void onDataNotAvailable();

    }

    interface LoadReviewsCallback{
        void onReviewsLoaded(List<Review> reviews);

        void onDataNotAvailable();
    }



    interface GetPosterCallback {
        void onPosterLoaded(Poster poster);

        void onDataNotAvailable();

    }

    void getPosters(@NonNull LoadPostersCallback callback, PosterFilterType filtering);

    void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback);

    void savePoster(@NonNull Poster poster);

    void refreshPosters();

    void deleteAllPosters();

    void deletePoster(String posterId);

    void getReviews(@NonNull LoadReviewsCallback callback, String posterId);

    Observable<Trailer> getTrailer(@NonNull String posterId);

    void refreshReviews();


}
