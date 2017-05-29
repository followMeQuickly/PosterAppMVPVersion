package com.example.chaseland.moviepostermvp.posterDetail;

import android.util.Log;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.data.source.Trailer;

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
        posterRepository.getReviews(new PosterSource.LoadReviewsCallback() {
            @Override
            public void onReviewsLoaded(List<Review> reviews) {
                List<Review> reviewsToShow = new ArrayList<Review>();
                for(int i =0; i <  reviews.size(); i++) {
                    Review review = reviews.get(i);
                    reviewsToShow.add(review);
                }
                processReviews(reviewsToShow);

            }

            @Override
            public void onDataNotAvailable() {

            }

        }, posterId);

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

        posterRepository.getPoster(posterId, new PosterSource.GetPosterCallback() {
            @Override
            public void onPosterLoaded(Poster poster) {
                if (poster != null) {
                    displayPoster(poster);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void displayPoster(Poster poster) {

        posterDetailView.showPosterDetails(poster);
        posterDetailView.showPosterImage(poster.getImagePath());
        //todo: add rest of shows here
    }
}
