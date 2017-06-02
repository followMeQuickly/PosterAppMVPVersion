package data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Posters;
import com.example.chaseland.moviepostermvp.data.Reviews;
import com.example.chaseland.moviepostermvp.data.Trailer;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;

import rx.Observable;

/**
 * Created by chaseland on 2/8/17.
 */

public class FakePosterRemoteDataSource implements PosterSource {


    private static FakePosterRemoteDataSource INSTANCE;
    public static FakePosterRemoteDataSource getInstance(){
        if (INSTANCE == null) {

            INSTANCE = new FakePosterRemoteDataSource();

        }

        return INSTANCE;
    }

    private FakePosterRemoteDataSource() {

    }

    @Override
    public void refreshTrailers() {

    }

    @Override
    public void getPosters(@NonNull LoadPostersCallback callback, PosterFilterType filtering) {

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
        return null;
    }

    @Override
    public Observable<Posters> getPosters(PosterFilterType filtering) {
        return null;
    }

    @Override
    public Observable<Reviews> getReviews(String posterId) {
        return null;
    }

    @Override
    public void refreshReviews() {

    }


    @VisibleForTesting
    public void addPosters(Poster... posters) {

    }
}
