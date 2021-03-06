package com.example.chaseland.moviepostermvp;

import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.posterDetail.PosterDetailContract;
import com.example.chaseland.moviepostermvp.posterDetail.PosterDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * Created by chaseland on 4/19/17.
 */

public class PosterDetailPresenterTests {
    private static List<Review> reviews;

    @Mock
    private PosterRepository posterRepository;

    @Mock
    private PosterDetailContract.PosterDetailView view;

    @Captor
    private ArgumentCaptor<PosterSource.LoadReviewsCallback> loadReviewsCallbackArgementCaptor;


    private PosterDetailPresenter presenter;


    @Before
    public void SetupPostersPresenter(){
        MockitoAnnotations.initMocks(this);
        presenter = new PosterDetailPresenter("",view, posterRepository);



    }

    @Test
    public void TestTheThings() {

    }





}

