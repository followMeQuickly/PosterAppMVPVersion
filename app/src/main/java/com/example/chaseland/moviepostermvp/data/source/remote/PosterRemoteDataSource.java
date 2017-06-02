package com.example.chaseland.moviepostermvp.data.source.remote;

import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.PosterService;
import com.example.chaseland.moviepostermvp.data.Posters;
import com.example.chaseland.moviepostermvp.data.Reviews;
import com.example.chaseland.moviepostermvp.data.Trailer;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by chaseland on 1/1/17.
 */

public class PosterRemoteDataSource implements PosterSource {

    private static PosterRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLI_SECONDS = 5000;

    private static final Map<String, Poster> POSTER_SERVICE_DATA;


    // note that Chet Haus? says that static constructors should be avoided. careful here.
    static {
        POSTER_SERVICE_DATA = new LinkedHashMap<>();
    }
    private final Retrofit retrofit;
    private String api_key = "91c43b8e8e40b5eefb9be99aaad68cd4";;

    private void LoadPosterData(@NonNull LoadPostersCallback callback, final PosterFilterType filtering) {
        List<Poster> posters = new ArrayList<>();
    }
    public static PosterRemoteDataSource GetInstance(){
        if(INSTANCE == null){
            INSTANCE = new PosterRemoteDataSource();
        }
        return INSTANCE;
    }
    private PosterRemoteDataSource(){
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .build();

    }


    @Override
    public void refreshTrailers() {

    }

    @Override
    public void getPosters(@NonNull LoadPostersCallback callback, PosterFilterType filtering)  {
        LoadPosterData(callback, filtering);

    }



    @Override
    public void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback) {

    }

    @Override
    public void savePoster(@NonNull Poster poster) {

    }

    @Override
    public void refreshPosters() {

    }

    @Override
    public void deleteAllPosters() {

    }

    @Override
    public void deletePoster(String posterId) {

    }

    @Override
    public Observable<Trailer> getTrailer(@NonNull String posterId) {

        PosterService trailerService = retrofit.create(PosterService.class);
        return trailerService.getTrailerData(posterId,api_key);

    }

    @Override
    public Observable<Posters> getPosters(PosterFilterType filtering) {
        String filter = "";
        switch (filtering){
            case POPULAR:
                filter = "popular";
                break;
            case HIGHEST_RATED:
                filter = "top_rated";
                break;
            default:
                filter = "popular";
                break;
        }
        PosterService posterService = retrofit.create(PosterService.class);
        return posterService.getPosters(filter, api_key

        );

    }

    @Override
    public Observable<Reviews> getReviews(String posterId) {
        PosterService posterService = retrofit.create(PosterService.class);
        return posterService.getReviewData(posterId, api_key);
    }

    @Override
    public void refreshReviews() {

    }
}
