package com.example.chaseland.moviepostermvp.posterDetail;

import android.util.Log;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Reviews;
import com.example.chaseland.moviepostermvp.data.Trailer;
import com.example.chaseland.moviepostermvp.data.Youtube;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by chaseland on 1/31/17.
 */

public class PosterDetailPresenter implements PosterDetailContract.Presenter {

    private final PosterRepository posterRepository;

    private final PosterDetailContract.PosterDetailView posterDetailView;

    private String posterId;

    private Poster poster;

    private boolean firstLoad = true;

    public PosterDetailPresenter(String posterId,
                                 PosterDetailContract.PosterDetailView posterDetailView,
                                 PosterRepository posterRepository) {

        this.posterId = posterId;

        this.posterRepository = posterRepository;
        this.posterDetailView = posterDetailView;
        this.posterDetailView.setPresenter(this);
    }

    @Override
    public void FavoritePoster(Poster poster) {

    }

    @Override
    public void loadReviews(boolean forceUpdate) {
        loadReviews(forceUpdate || firstLoad, true);
        forceUpdate = false;


    }

    private void loadReviews(boolean forceUpdate, boolean showLoadinUi) {

        // todo: add in check if the loading ui should be shown.
        //todo: add in check for if a forced update is needed
        posterRepository.getReviews(posterId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reviews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Reviews reviews) {
                        Log.d(TAG, "onNext: " + reviews.getReviews());
                        processReviews(reviews.getReviews());

                    }
                });

    }

    private void processReviews(List<Review> reviewsToShow) {
        posterDetailView.showReviews(reviewsToShow);
    }
    @Override
    public void loadTrailers(boolean forceUpdate) {
        // todo: add in check if the loading ui should be shown.
        //todo: add in check for if a forced update is needed
        loadTrailers(forceUpdate, true);

    }
    private void loadTrailers(boolean forceUpdate, boolean showLoadingUi) {
        final List<Youtube> youtubeList = new ArrayList<Youtube>();
        posterRepository.getTrailer(posterId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Trailer>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Trailer trailer) {
                        Log.d(TAG, "onNext: " + trailer.getYoutube());
                        posterDetailView.showTrailers(trailer.getYoutube());

                        //posterDetailView.showTrailerImage(imageUrl);


                    }
                });
    }

    @Override
    public void Start() {
        loadReviews(false);
        loadTrailers(false);
        showPoster();

    }

    private void showPoster() {

        final Poster[] x = {null};
        posterRepository.getPoster(posterId, new PosterSource.GetPosterCallback() {
            @Override
            public void onPosterLoaded(Poster poster) {
                if (poster != null) {
                    x[0] = poster;
                    displayPoster(poster);

                    posterDetailView.showTrailerImage(poster.getBackdropPath());
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        this.poster = x[0];
    }

    private void displayPoster(Poster poster) {

        posterDetailView.showPosterDetails(poster);
        posterDetailView.showPosterImage(poster.getBackdropPath());
        //todo: add rest of shows here
    }
}
