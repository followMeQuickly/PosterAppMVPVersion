package com.example.chaseland.moviepostermvp.posterDetail;

import com.example.chaseland.moviepostermvp.BasePresenter;
import com.example.chaseland.moviepostermvp.BaseView;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Youtube;

import java.util.List;

/**
 * Created by chaseland on 1/30/17.
 */

public interface PosterDetailContract {
    interface PosterDetailView extends BaseView<PosterDetailContract.Presenter>{


        void showPosterImage(String imageUrl);

        void showPosterBackdrop(String imageUrl);

        void favoritePoster();

        void showPosterDetails(Poster posterDetails);

        void hideTitle();

        void showReviews(List<Review> review);


        void showTrailerImage(String imageUrl);



        void hideReviews();

        void showTrailers(List<Youtube> trailers);



    }
    interface Presenter extends BasePresenter{

        void FavoritePoster(Poster poster);

        void loadReviews(boolean forceUpdate);

        void loadTrailers(boolean forceUpdate);
    }
}
