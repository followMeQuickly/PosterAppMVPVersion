package com.example.chaseland.moviepostermvp.posterDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Youtube;
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

//    private TextView TitleTextView;
    private ImageView BackdropPosterImage;
    private ImageView PosterImage;
//    private RecyclerView ReviewRecyclerView;
//    private RecyclerView TrailerRecyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout;


    private ReviewRecyclerAdapter ReviewRecycleAdapter;
    private TrailerRecyclerAdapter TrailerRecyclerAdapter;

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

        this.TrailerRecyclerAdapter = new TrailerRecyclerAdapter(new ArrayList<Youtube>(0), getContext(), new TrailerItemListener() {
            @Override
            public void onClick(Youtube youtube) {
                //todo: implement open trailer on youtube
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_poster_detail, container, false);
//        TitleTextView = (TextView)root.findViewById(R.id.poster_detail_title);
        BackdropPosterImage = (ImageView) root.findViewById(R.id.poster_detail_image);
        PosterImage = (ImageView) root.findViewById(R.id.poster_image);
        Toolbar tb = (android.widget.Toolbar) root.findViewById(R.id.toolbar);

        tb.setTitle("title");

        getActivity().setActionBar(tb);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActivity().getActionBar().setDisplayShowHomeEnabled();


//        initReviewRecycler(root);
//        initTrailerRecycler(root);


        collapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("title");





        return root;
    }

//    private void initTrailerRecycler(View root) {
//        TrailerRecyclerView = (RecyclerView) root.findViewById(R.id.trailer_list);
//        TrailerRecyclerView.setAdapter(TrailerRecyclerAdapter);
//        TrailerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//    }
//
//    private void initReviewRecycler(View root) {
//        ReviewRecyclerView = (RecyclerView)root.findViewById(R.id.review_list);
//        ReviewRecyclerView.setAdapter(ReviewRecycleAdapter);
//        ReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//    }


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
                .into(PosterImage);
    }

    @Override
    public void showPosterBackdrop(String imageUrl) {

       // BackdropPosterImage.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));
        Picasso.with(getActivity())
                .load(imageUrl)
                .fit()
                .into(BackdropPosterImage);

    }


    @Override
    public void favoritePoster() {

    }

    @Override
    public void showPosterDetails(Poster posterDetails) {
        collapsingToolbarLayout.setTitle(posterDetails.getTitle());
//        TitleTextView.setVisibility(View.VISIBLE);
//        TitleTextView.setText(posterDetails.getTitle());

    }

    @Override
    public void hideTitle() {
//        TitleTextView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showReviews(List<Review> reviews) {
        ReviewRecycleAdapter.replacePosters(reviews);
    }

    @Override
    public void showTrailerImage(String imageUrl) {


    }

    @Override
    public void hideReviews() {

    }

    @Override
    public void showTrailers(List<Youtube> trailers) {

        TrailerRecyclerAdapter.replaceTrailers(trailers);
    }

    @Override
    public void setPresenter(PosterDetailContract.Presenter presenter) {
        this.Presenter = presenter;
    }
    public class TrailerHolder extends RecyclerView.ViewHolder{

        TrailerItemListener listener;
        private View view;

        public TrailerHolder(View itemView, TrailerItemListener listener) {
            super(itemView);
            this.listener = listener;
            this.view = itemView;

        }
        public void setTrailerInfo(final Youtube trailer){
            ImageView trailerThumbnail = ((ImageView) view.findViewById(R.id.trailer_thumbnail));
            Picasso.with(view.getContext())
                    .load("https://img.youtube.com/vi/" + trailer.getSource() + "/1.jpg")
                    .into(trailerThumbnail);
            trailerThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(trailer);
                }
            });


        }
    }

    public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerHolder> {

        private List<Youtube> youtubeList;
        private Context context;
        private TrailerItemListener listener;
        public TrailerRecyclerAdapter(List<Youtube> youtubeList, Context context, TrailerItemListener listener){
            this.youtubeList = youtubeList;
            this.context = context;
            this.listener = listener;

        }
        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.trailer_item, parent, false);
            TrailerHolder trailerHolder = new TrailerHolder(view, listener);


            return trailerHolder;
        }

        public void replaceTrailers(List<Youtube> youtubeList) {
            this.youtubeList = youtubeList;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {
            holder.setTrailerInfo(youtubeList.get(position));

        }

        @Override
        public int getItemCount() {
            return youtubeList.size();
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
            TextView authorTextView = (TextView) itemView.findViewById(R.id.review_author);
            authorTextView.setText(review.getAuthor());
            TextView contentTextView = (TextView) itemView.findViewById(R.id.review_content);
            contentTextView.setText(review.getContent());
        }
    }

    public interface ReviewItemListener{
        void onClick(Review review);
    }
    public interface TrailerItemListener{
        void onClick(Youtube trailer);
    }
}
