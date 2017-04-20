package com.example.chaseland.moviepostermvp.posterDetail;

import com.example.chaseland.moviepostermvp.BasePresenter;
import com.example.chaseland.moviepostermvp.BaseView;
import com.example.chaseland.moviepostermvp.data.Poster;

/**
 * Created by chaseland on 1/30/17.
 */

public interface PosterDetailContract {
    interface PosterDetailView extends BaseView<PosterDetailContract.Presenter>{


        void showPosterImage(String imageUrl);

        void favoritePoster();

        void showTitle(String Title);

        void hideTitle();

        void showReviews();

        void hideReviews();

        void launchTrailers();

    }
    interface Presenter extends BasePresenter{

        void FavoritePoster(Poster poster);

        void loadReviews(boolean forceUpdate);

        void loadTrailers(boolean forceUpdate);
    }
}