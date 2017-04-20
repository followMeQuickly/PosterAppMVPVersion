package com.example.chaseland.moviepostermvp.posters;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaseland on 1/11/17.
 */

public class PostersPresenter implements PostersContract.Presenter {


    private final PosterRepository posterRepository;

    private final PostersContract.PosterView posterView;

    private PosterFilterType currentFiltering = PosterFilterType.ALL_POSTERS;


    private static int x;


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
        if(forceUpdate){
            posterRepository.refreshPosters();
        }

        // todo: when implementing espresso tests, be sure to add wait here for network response
        posterRepository.getPosters(new PosterSource.LoadPostersCallback() {
            @Override
            public void onPostersLoaded(List<Poster> posters) {

                List<Poster> postersToShow = new ArrayList<Poster>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.


                // do not use foreach loops because chet haase says so
                for(int i = 0; i < posters.size(); i++){
                    Poster poster = posters.get(i);
                    switch (currentFiltering){
                        case ALL_POSTERS:
                            postersToShow.add(poster);
                            break;
                        default:
                            // log weird error
                            postersToShow.add(poster);
                            break;


                    }

                }
//                if (!posterView.isActive()){
//                    return;
//
//                }

                processPosters(postersToShow);
            }

            

            @Override
            public void onDataNotAvailable() {
//

            }
        }, currentFiltering);
    }

    private void processPosters(List<Poster> postersToShow) {
        if(postersToShow.isEmpty()){

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

    @Override
    public void OpenPosterDetails(Poster requestedPoster) {
        posterView.showPosterDetailsUI(requestedPoster.getId());

    }
}
