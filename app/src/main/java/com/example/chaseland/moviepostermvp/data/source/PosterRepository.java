package com.example.chaseland.moviepostermvp.data.source;

import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaseland on 12/28/16.
 */

public class PosterRepository implements PosterSource {


    //todo: rename poster remote data source to RemotePosterDataSource
    private PosterSource localRepo;
    private PosterSource remoteRepo;
    public static PosterRepository INSTANCE = null;

    Map<String, Poster> cachedPosters;

    boolean isCacheDirty = false;

    private PosterRepository(PosterSource localSource, PosterSource remoteSource){
        this.localRepo =  localSource;
        this.remoteRepo = remoteSource;

    }

    public static PosterRepository GetInstance(PosterSource localSource, PosterSource remoteSource){
        if(INSTANCE == null){
            INSTANCE = new PosterRepository(localSource, remoteSource);
        }
        return INSTANCE;

    }
    @Override
    public void getPosters(@NonNull final LoadPostersCallback callback, final PosterFilterType filtering) {

        if(cachedPosters != null && !isCacheDirty){
            callback.onPostersLoaded(new ArrayList<Poster>(cachedPosters.values()));
            return;
        }

        if(isCacheDirty){
            getPostersFromRemoteDataSource(callback, filtering);
        }
        else {
            localRepo.getPosters(new LoadPostersCallback() {
                @Override
                public void onPostersLoaded(List<Poster> posters) {
                    refreshPosters();
                    callback.onPostersLoaded(new ArrayList<Poster>(cachedPosters.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getPostersFromRemoteDataSource(callback, filtering);

                }
            },filtering);
        }

    }

    private void getPostersFromRemoteDataSource(final LoadPostersCallback callback, PosterFilterType filterType) {
        remoteRepo.getPosters(new LoadPostersCallback() {
            @Override
            public void onPostersLoaded(List<Poster> posters) {
                refreshCache(posters);
                refreshLocalDataSource(posters);
                callback.onPostersLoaded(new ArrayList<Poster>(posters));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();

            }
        }, filterType);
    }

    private void refreshLocalDataSource(List<Poster> posters){

        localRepo.deleteAllPosters();
        //todo: add filter type to database and update.
        for (Poster poster: posters) {
            localRepo.savePoster(poster);

        }
    }

    private void refreshCache(List<Poster> posters) {

        if(cachedPosters == null){
            cachedPosters = new LinkedHashMap<>();
        }
        cachedPosters.clear();
        for (Poster poster: posters) {
            cachedPosters.put(poster.getId(), poster);

        }
        isCacheDirty = false;
    }

    @Override
    public void getPoster(@NonNull String posterId, @NonNull GetPosterCallback callback) {
        localRepo.getPoster(posterId, callback);

    }

    @Override
    public void savePoster(@NonNull Poster poster) {

    }

    @Override
    public void refreshPosters() {
        isCacheDirty = true;



    }

    @Override
    public void deleteAllPosters() {

    }

    @Override
    public void deletePoster(String posterId) {

    }
}
