package com.example.chaseland.moviepostermvp.posterDetail;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;

/**
 * Created by chaseland on 1/31/17.
 */

public class PosterDetailPresenter implements PosterDetailContract.Presenter {

    private final PosterRepository posterRepository;

    private final PosterDetailContract.PosterDetailView posterDetailView;

    private String posterId;

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

    }

    @Override
    public void loadTrailers(boolean forceUpdate) {

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
        String title = poster.getTitle();

        posterDetailView.showTitle(title);
        posterDetailView.showPosterImage(poster.getImagePath());
        //todo: add rest of shows here
    }
}
