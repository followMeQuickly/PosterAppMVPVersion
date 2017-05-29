package com.example.chaseland.moviepostermvp.data.source.remote;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.source.PosterSource;
import com.example.chaseland.moviepostermvp.data.source.Trailer;
import com.example.chaseland.moviepostermvp.data.source.TrailerService;
import com.example.chaseland.moviepostermvp.posters.PosterFilterType;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    public boolean beenCalled = false;
    private final Retrofit retrofit;
    private String api_key = "91c43b8e8e40b5eefb9be99aaad68cd4";;

    private void LoadPosterData(@NonNull LoadPostersCallback callback, final PosterFilterType filtering) {
        List<Poster> posters = new ArrayList<>();
        AsyncTask<Void, Void, List<Poster>> posterTask = new AsyncTask<Void, Void, List<Poster>>() {

            private List<Poster> posters;

            @Override
            protected List<Poster> doInBackground(Void... params) {
                posters = new ArrayList<>();
                OkHttpClient client = new OkHttpClient();
                String url = "";
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.themoviedb.org").newBuilder();
                urlBuilder.addPathSegment("3");
                urlBuilder.addPathSegment("movie");
                switch (filtering){
                    case POPULAR:
                        urlBuilder.addPathSegment("popular");
                        break;
                    case HIGHEST_RATED:
                        urlBuilder.addPathSegment("top_rated");
                        break;
                    default:
                        urlBuilder.addPathSegment("popular");
                }


                urlBuilder.addQueryParameter("api_key", api_key);
                url = urlBuilder.build().toString();
                Request posterRequest = new Request.Builder().url(url).build();

                try{
                    //todo: use
                    Response posterResponse = client.newCall(posterRequest).execute();
                    ResponseBody body = posterResponse.body();
                    JSONObject object = new JSONObject(body.string());
                    JSONArray array = object.getJSONArray("results");
                    String baseImagePath = "http://image.tmdb.org/t/p/w154//";

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject currentObject = (JSONObject) array.get(i);
                        Integer id = currentObject.getInt("id");
                        String posterPath = baseImagePath + currentObject.getString("poster_path");
                        String posterDescription = currentObject.getString("overview");
                        String vote = currentObject.getString("vote_average");
                        String releaseDate = currentObject.getString("release_date");
                        String title = currentObject.getString("title");
                        Poster currentPoster = new Poster(id.toString(), title, posterDescription, false, Double.valueOf(vote),releaseDate, posterPath);
                        posters.add(currentPoster);
                    }
                }
                catch (IOException exception){
                    // todo: add log statement for unexpected network exception
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return posters;
            }

        };

        posterTask.execute();

        try {
            posters = posterTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        callback.onPostersLoaded(posters);
        beenCalled = true;
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
    public void getReviews(@NonNull LoadReviewsCallback callback, String posterId) {
        List<Review> reviews = new ArrayList<>();
        AsyncTask<String, Void, List<Review>> reviewTask = new AsyncTask<String, Void, List<Review>>() {

            private List<Review> reviews;

            @Override
            protected List<Review> doInBackground(String... params) {
                String movieId = params[0];
                reviews = new ArrayList<>();
                OkHttpClient client = new OkHttpClient();
                String url = "";
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.themoviedb.org").newBuilder();
                urlBuilder.addPathSegment("3");
                urlBuilder.addPathSegment("movie");
                urlBuilder.addPathSegment(movieId);
                urlBuilder.addPathSegment("reviews");
                urlBuilder.addQueryParameter("api_key", "91c43b8e8e40b5eefb9be99aaad68cd4");

                url = urlBuilder.build().toString();
                Request reviewRequest = new Request.Builder().url(url).build();

                try{
                    Response reviewResponse = client.newCall(reviewRequest).execute();
                    ResponseBody body = reviewResponse.body();
                    JSONObject object = new JSONObject(body.string());
                    JSONArray array = object.getJSONArray("results");

                    for(int i =0; i < array.length(); i++) {
                        JSONObject currentObject = (JSONObject) array.get(i);
                        String author = currentObject.getString("author");
                        String content = currentObject.getString("content");
                        Review review = new Review(author, content);
                        reviews.add(review);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return reviews;
            }

        };

        reviewTask.execute(posterId);

        try {
            reviews = reviewTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        callback.onReviewsLoaded(reviews);
        beenCalled = true;
    }

    @Override
    public Observable<Trailer> getTrailer(@NonNull String posterId) {

        TrailerService trailerService = retrofit.create(TrailerService.class);
        return trailerService.getTrailerData(posterId,api_key);

    }

    @Override
    public void refreshReviews() {

    }
}
