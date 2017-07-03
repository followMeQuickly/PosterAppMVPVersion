package com.example.chaseland.moviepostermvp.posters;

import android.util.Log;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Posters;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by chaseland on 1/11/17.
 */

public class PostersPresenter implements PostersContract.Presenter {


    private final PosterRepository posterRepository;

    private final PostersContract.PosterView posterView;

    private PosterFilterType currentFiltering = PosterFilterType.ALL_POSTERS;

    private boolean mFirstLoad = true;

    public PostersPresenter(PosterRepository posterRepository, PostersContract.PosterView posterView) {
        this.posterRepository = posterRepository;
        this.posterView = posterView;
        this.posterView.setPresenter(this);
        System.out.println();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadPosters(boolean forceUpdate) {

        loadPosters(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;

    }

    private void loadPosters(boolean forceUpdate, boolean showLoadingUi) {
        if(showLoadingUi){
            posterView.setLoadingIndicator(true);
        }

        posterRepository.getPosters(currentFiltering)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Posters>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError:" +
                                e.getMessage());
                    }

                    @Override
                    public void onNext(Posters poster) {
                        Log.d(TAG, "onNext: " + poster.getResults());
                        processPosters(poster.getResults());
                    }
                });
    }

    private void processPosters(List<Poster> postersToShow) {
        for (Poster poster: postersToShow) {
            posterRepository.savePoster(poster);

        }
        posterView.showPosters(postersToShow);
    }

    @Override
    public void Start() {
        loadPosters(false);

    }

    @Override
    public void favoritePoster(Poster favoritedPoster) {

    }

    @Override
    public void changeFilterType(PosterFilterType filterType) {
        currentFiltering = filterType;
    }

    public void OpenPosterDetails(Poster requestedPoster) {
        posterView.showPosterDetailsUI(requestedPoster);

    }
}
