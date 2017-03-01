package com.example.chaseland.moviepostermvp;

import android.content.Context;

import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.local.LocalPosterDataSource;
import com.example.chaseland.moviepostermvp.data.source.remote.PosterRemoteDataSource;

/**
 * Created by chaseland on 2/7/17.
 */

public class Injection {
    public static PosterRepository provideRepository(Context context) {
        return PosterRepository.GetInstance(LocalPosterDataSource.getInstance(context),
                PosterRemoteDataSource.GetInstance());
    }
}
