package com.example.chaseland.moviepostermvp.posterDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Youtube;
import com.example.chaseland.moviepostermvp.data.source.PosterRepository;
import com.example.chaseland.moviepostermvp.data.source.local.LocalPosterDataSource;
import com.example.chaseland.moviepostermvp.data.source.remote.PosterRemoteDataSource;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chaseland on 1/31/17.
 */

public class PosterDetailActivity extends AppCompatActivity  implements PosterDetailContract.PosterDetailView{

    public static final String TASK_DETAIL_ID = "poster detail";

    private ImageView BackdropPosterImage;
    private PosterDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_detail);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String posterId = getIntent().getStringExtra(TASK_DETAIL_ID);


        PosterRepository repo = PosterRepository.GetInstance(LocalPosterDataSource.getInstance(this), PosterRemoteDataSource.GetInstance());


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("title");

        BackdropPosterImage = (ImageView) findViewById(R.id.poster_detail_image);
        new PosterDetailPresenter(posterId, this, repo).Start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showPosterImage(String imageUrl) {

    }

    @Override
    public void showPosterBackdrop(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .into(BackdropPosterImage);

    }

    @Override
    public void favoritePoster() {

    }

    @Override
    public void showPosterDetails(Poster posterDetails) {

    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showReviews(List<Review> review) {

    }

    @Override
    public void showTrailerImage(String imageUrl) {

    }

    @Override
    public void hideReviews() {

    }

    @Override
    public void showTrailers(List<Youtube> trailers) {

    }

    @Override
    public void setPresenter(PosterDetailContract.Presenter presenter) {
        this.presenter = presenter;

    }
}
