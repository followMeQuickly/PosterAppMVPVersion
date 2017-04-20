package com.example.chaseland.moviepostermvp.posterDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaseland.moviepostermvp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by chaseland on 2/2/17.
 */

public class PosterDetailFragment extends Fragment implements PosterDetailContract.PosterDetailView{

    private PosterDetailContract.Presenter Presenter;

    private static final String POSTER_IMAGE_TRANSITION_KEY = "transition";
    private static final String POSTER_ID_KEY = "PosterId";

    private TextView TitleTextView;
    private ImageView PosterImageView;


    public static PosterDetailFragment newInstance(String id){
        Bundle args = new Bundle();
        args.putString(POSTER_ID_KEY, id);
        PosterDetailFragment fragment = new PosterDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().supportStartPostponedEnterTransition();;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        TitleTextView = (TextView)root.findViewById(R.id.poster_detail_title);
        PosterImageView = (ImageView) root.findViewById(R.id.poster_detail_image);

        return root;
    }



    @Override
    public void onResume() {
        super.onResume();
        Presenter.Start();
    }


    @Override
    public void showPosterImage(String imageUrl) {
        //todo: create interface to abstract away loading images
        Picasso.with(getActivity())
                .load(imageUrl)
                .fit()
                .into(PosterImageView);
    }

    @Override
    public void favoritePoster() {

    }

    @Override
    public void showTitle(String title) {
        TitleTextView.setVisibility(View.VISIBLE);
        TitleTextView.setText(title);

    }

    @Override
    public void hideTitle() {
        TitleTextView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showReviews() {

    }

    @Override
    public void hideReviews() {

    }

    @Override
    public void launchTrailers() {

    }

    @Override
    public void setPresenter(PosterDetailContract.Presenter presenter) {
        this.Presenter = presenter;
    }
}
