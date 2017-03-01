package com.example.chaseland.moviepostermvp.data.source;

import android.content.Context;

import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by chaseland on 2/15/17.
 */

public class PosterRepositoryTests {

    private PosterRepository posterRepository;

    @Mock
    private PosterSource localPosterRepo;

    @Mock
    private PosterSource remotePosterRepo;

    @Mock
    private Context context;

    @Mock
    private PosterSource.GetPosterCallback getPosterCallback;

    @Mock
    private PosterSource.LoadPostersCallback loadPostersCallback;

    @Captor
    private ArgumentCaptor<PosterSource.LoadPostersCallback> loadPostersCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<PosterSource.GetPosterCallback> getPosterCallbackArgumentCaptor;

    @Before
    public void Setup() {
        MockitoAnnotations.initMocks(this);

        posterRepository = PosterRepository.GetInstance(localPosterRepo, remotePosterRepo);
    }

    @After
    public void TearDown() {
        //todo: make destroy instance method in repository
    }

    @Test
    public void TestGetPosters() {
        posterRepository.refreshPosters();
        posterRepository.getPosters(loadPostersCallback, PosterFilterType.POPULAR);
        verify(remotePosterRepo).getPosters(any(PosterSource.LoadPostersCallback.class), any(PosterFilterType.class));
    }
}
