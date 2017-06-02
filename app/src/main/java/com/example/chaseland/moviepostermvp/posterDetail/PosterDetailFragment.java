package com.example.chaseland.moviepostermvp.posterDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaseland on 2/2/17.
 */

public class PosterDetailFragment extends Fragment implements PosterDetailContract.PosterDetailView{

    private PosterDetailContract.Presenter Presenter;

    private static final String POSTER_IMAGE_TRANSITION_KEY = "transition";
    private static final String POSTER_ID_KEY = "PosterId";

    private TextView TitleTextView;
    private ImageView PosterImageView;
    private RecyclerView ReviewRecyclerView;
    private RecyclerView TrailerRecyclerView;

    private ReviewRecyclerAdapter ReviewRecycleAdapter;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ReviewRecycleAdapter = new ReviewRecyclerAdapter(new ArrayList<Review>(0), getContext(), new ReviewItemListener() {
            @Override
            public void onClick(Review review) {
                //todo: implement open review details method in presenter
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        TitleTextView = (TextView)root.findViewById(R.id.poster_detail_title);
        PosterImageView = (ImageView) root.findViewById(R.id.poster_detail_image);
        ReviewRecyclerView = (RecyclerView)root.findViewById(R.id.review_list);
        ReviewRecyclerView.setAdapter(ReviewRecycleAdapter);
        ReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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
    }

    @Override
    public void favoritePoster() {

    }

    @Override
    public void showPosterDetails(Poster posterDetails) {
        TitleTextView.setVisibility(View.VISIBLE);
        TitleTextView.setText(posterDetails.getTitle());

    }

    @Override
    public void hideTitle() {
        TitleTextView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showReviews(List<Review> reviews) {

        ReviewRecycleAdapter.replacePosters(reviews);


    }

    @Override
    public void showTrailerImage(String imageUrl) {

        Picasso.with(getActivity())
                .load(imageUrl)
                .fit()
                .into(PosterImageView);

    }

    @Override
    public void hideReviews() {

    }

    @Override
    public void showTrailers(List<Trailer> trailers) {

    }

    @Override
    public void setPresenter(PosterDetailContract.Presenter presenter) {
        this.Presenter = presenter;
    }
    public class TrailerHolder extends RecyclerView.ViewHolder{

        public TrailerHolder(View itemView) {
            super(itemView);
        }
    }

    public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerHolder> {

        private List<Trailer> trailers;
        private Context context;
        private TrailerItemListener listener;
        public TrailerRecyclerAdapter(List<Trailer> trailer, Context context, TrailerItemListener listener){
            this.trailers = trailer;
            this.context = context;
            this.listener = listener;

        }
        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return null;
        }


        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
    public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewHolder> {

        private List<Review> reviews;
        private ReviewItemListener listener;

        private Context context;
        public ReviewRecyclerAdapter(List<Review> reviews, Context context, ReviewItemListener listener) {
            this.reviews = reviews;
            this.context = context;
            this.listener = listener;
        }

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.review_item, parent, false);
            ReviewHolder reviewHolder = new ReviewHolder(view,  listener);
            return reviewHolder;
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {

            holder.setReviewInfo(reviews.get(position));
        }

        public void replacePosters(List<Review> reviews) {
            this.reviews = reviews;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private ReviewItemListener reviewItemListener;
        private View itemView;
        public ReviewHolder(View itemView, ReviewItemListener listener) {
            super(itemView);
            this.itemView = itemView;
            this.reviewItemListener = listener;

        }

        public void setReviewInfo(final Review review) {
            TextView textView = (TextView) itemView.findViewById(R.id.review_author);
            textView.setText(review.getAuthor());
        }
    }

    public interface ReviewItemListener{
        void onClick(Review review);
    }
    public interface TrailerItemListener{
        void onClick(Trailer trailer);
    }
}
