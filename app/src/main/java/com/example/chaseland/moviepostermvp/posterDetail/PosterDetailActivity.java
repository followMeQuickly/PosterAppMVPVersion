package com.example.chaseland.moviepostermvp.posterDetail;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.Util.ActivityUtils;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.local.LocalPosterDataSource;
import com.example.chaseland.moviepostermvp.data.source.remote.PosterRemoteDataSource;

/**
 * Created by chaseland on 1/31/17.
 */

public class PosterDetailActivity extends AppCompatActivity {

    public static final String TASK_DETAIL_ID = "poster detail";

    public static final String TASK_DETAIL_TRANSITION_NAME = "poster transition name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_detail);
        PosterDetailFragment fragment = (PosterDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String posterId = getIntent().getStringExtra(TASK_DETAIL_ID);
        String transitionName = getIntent().getStringExtra(TASK_DETAIL_TRANSITION_NAME);
        if (fragment == null) {
            fragment = PosterDetailFragment.newInstance(posterId, transitionName);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);

        }
        PosterRepository repo = PosterRepository.GetInstance(LocalPosterDataSource.getInstance(this), PosterRemoteDataSource.GetInstance());

        new PosterDetailPresenter(posterId, fragment, repo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }
}
