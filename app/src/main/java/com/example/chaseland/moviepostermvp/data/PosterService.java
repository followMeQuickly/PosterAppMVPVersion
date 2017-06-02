package com.example.chaseland.moviepostermvp.data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chaseland on 5/29/17.
 */

public interface PosterService {

    @GET("3/movie/{filter}")
    Observable<Posters> getPosters(@Path("filter") String filter, @Query("api_key") String api_key);

    @GET("/3/movie/{posterId}/trailers")
    Observable<Trailer> getTrailerData(@Path("posterId") String posterId, @Query("api_key")String apiKey);

    @GET("/3/movie/{posterId}/reviews")
    Observable<Reviews> getReviewData(@Path("posterId") String posterId, @Query("api_key")String apiKey);

}
