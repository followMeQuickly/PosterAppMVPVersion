package com.example.chaseland.moviepostermvp.data.source;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chaseland on 5/27/17.
 */

public interface TrailerService {

    @GET("/3/movie/{posterId}/trailers")
    Observable<Trailer> getTrailerData(@Path("posterId") String posterId, @Query("api_key")String apiKey);
}
