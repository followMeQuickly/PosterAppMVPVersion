package com.example.chaseland.moviepostermvp.posters;

import com.example.chaseland.moviepostermvp.BasePresenter;
import com.example.chaseland.moviepostermvp.BaseView;
import com.example.chaseland.moviepostermvp.data.Poster;

import java.util.List;

/**
 * Created by chaseland on 12/28/16.
 */

public interface PostersContract {
        interface PosterView extends BaseView<Presenter>{

        void showPosters(List<Poster> posters);

        void showFilteringPopMenu();

        void showPosterDetailsUI(Poster poster);

        void setLoadingIndicator(boolean active);

        void showFavoritedPosters();

        void showPostersLoadingError();

        boolean isActive();





        }
    interface Presenter extends BasePresenter{

        void result(int requestCode, int resultCode);

        void loadPosters(boolean forceUpdate);



        void favoritePoster(Poster favoritedPoster);

        void changeFilterType(PosterFilterType filterType);

        void OpenPosterDetails(Poster requestedPoster);
    }
}
