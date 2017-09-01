package com.example.chaseland.moviepostermvp;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Posters;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;
import com.example.chaseland.moviepostermvp.posters.PostersContract;
import com.example.chaseland.moviepostermvp.posters.PostersPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.internal.schedulers.ExecutorScheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        createSchedular();

        POSTERS = new ArrayList<Poster>();

        postersPresenter = new PostersPresenter(posterRepository, posterView);
    }
    @After
    public void tearDown(){
        RxAndroidPlugins.getInstance().reset();
    }

    private void createSchedular() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook(){
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void TestSetView()
    {
        postersPresenter = new PostersPresenter(posterRepository, posterView);
        verify(posterView).setPresenter(postersPresenter);
    }
    @Test
    public void TestLoadPosters() {
        when(posterRepository.getPosters(PosterFilterType.ALL_POSTERS))
                .thenReturn(Observable.just(new Posters()));

        postersPresenter.loadPosters(true);
        //verify(posterRepository).getPosters(loadPostersCallbackArgumentCaptor.capture(), PosterFilterType.POPULAR);
        verify(posterRepository).getPosters(PosterFilterType.ALL_POSTERS);
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
