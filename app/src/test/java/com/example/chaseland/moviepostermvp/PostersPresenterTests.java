package com.example.chaseland.moviepostermvp;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;
import com.example.chaseland.moviepostermvp.posters.PostersContract;
import com.example.chaseland.moviepostermvp.posters.PostersPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by chaseland on 2/7/17.
 */

public class PostersPresenterTests {

    private static List<Poster> POSTERS;

    @Mock
    private PosterRepository posterRepository;

    @Mock
    private PostersContract.PosterView posterView;

    @Captor
    private ArgumentCaptor<PosterSource.LoadPostersCallback> loadPostersCallbackArgumentCaptor;

    private PostersPresenter postersPresenter;

    @Before
    public void SetupPostersPresenter() {
        MockitoAnnotations.initMocks(this);

        POSTERS = new ArrayList<Poster>();

        postersPresenter = new PostersPresenter(posterRepository, posterView);
    }

    @Test
    public void TestLoadPosters() {
        postersPresenter.loadPosters(true);
        verify(posterRepository).getPosters(loadPostersCallbackArgumentCaptor.capture(), PosterFilterType.POPULAR);
    }

    @Test
    public void TestLoadPoster(){
        Poster testPoster = POSTERS.get(0);
        postersPresenter.OpenPosterDetails(testPoster);

    }

    @Test
    public void TestShowPosterDetails() {
        Poster testPoster = POSTERS.get(0);
        postersPresenter.OpenPosterDetails(testPoster);


    }

    @Test
    public void TestChangeFilterType(){
        postersPresenter.changeFilterType(PosterFilterType.HIGHEST_RATED);
        postersPresenter.loadPosters(true);

    }


}
