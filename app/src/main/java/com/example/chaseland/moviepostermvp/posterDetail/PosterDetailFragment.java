package com.example.chaseland.moviepostermvp.posterDetail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.data.Review;
import com.example.chaseland.moviepostermvp.data.Youtube;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.xml.transform.Source;

import static android.content.ContentValues.TAG;

/**
 * Created by chaseland on 2/2/17.
 */

public class PosterDetailFragment extends Fragment implements PosterDetailContract.PosterDetailView{

    private PosterDetailContract.Presenter Presenter;

    private static final String POSTER_IMAGE_TRANSITION_KEY = "transition";
    private static final String POSTER_ID_KEY = "PosterId";

    private TextView SummaryTextView;

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView PosterImageView;
    private ImageView PosterImageViewTest;
    private RecyclerView ReviewRecyclerView;
    private RecyclerView TrailerRecyclerView;

    private ReviewRecyclerAdapter ReviewRecycleAdapter;
    private YoutubeRecyclerViewAdapter youtubeRecyclerViewAdapter;

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
        this.youtubeRecyclerViewAdapter = new YoutubeRecyclerViewAdapter(new ArrayList<Youtube>(), getContext(), new YoutubeTrailerItemListener() {
            @Override
            public void onClick(Youtube youtube) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        SummaryTextView = (TextView) root.findViewById(R.id.poster_detail_summary);
        PosterImageView = (ImageView) root.findViewById(R.id.poster_detail_image);

        PosterImageViewTest = (ImageView) root.findViewById(R.id.poster_detail_image_2);
        ((AppCompatActivity) getActivity()).setSupportActionBar((android.support.v7.widget.Toolbar) root.findViewById(R.id.toolbar));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        collapsingToolbar = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(0));



        ReviewRecyclerView = (RecyclerView)root.findViewById(R.id.review_list);
        ReviewRecyclerView.setAdapter(ReviewRecycleAdapter);
        ReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        TrailerRecyclerView = (RecyclerView) root.findViewById(R.id.trailer_list);
        TrailerRecyclerView.setAdapter(youtubeRecyclerViewAdapter);
        TrailerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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
        imageUrl = "http://image.tmdb.org/t/p/w500" + imageUrl;
// this picasso call actually makes the image fit.
        //// TODO: 7/6/2017 figure out how to get image to fit with palette call.
//        new Picasso.Builder(getContext()).executor(Executors.newSingleThreadExecutor())
        Picasso.with(getContext())
                .load(imageUrl)
                .fit()
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        Palette.Builder builder = new Palette.Builder(source);
                        builder.setRegion(0, 0, PosterImageView.getWidth(), 50);
                        builder.maximumColorCount(2);
                        Palette palette = builder.generate();
                        //.collapsingToolbar.setContentScrimColor(palette.getSwatches().get(0).getRgb());
//                        collapsingToolbar.setBackgroundColor(palette.getVibrantColor(0));
                        return source;
                    }

                    @Override
                    public String key() {
                        return "";
                    }
                })
                .into(PosterImageView);



    }

    @Override
    public void favoritePoster() {

    }

    @Override
    public void showPosterDetails(Poster posterDetails) {

        SummaryTextView.setText(""+posterDetails.getOverview());
        collapsingToolbar.setTitle(posterDetails.getTitle());

    }

    @Override
    public void hideTitle() {


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
    public void showTrailers(List<Youtube> youtubeVideos) {

        youtubeRecyclerViewAdapter.replaceYoutubeList(youtubeVideos);

    }

    @Override
    public void setPresenter(PosterDetailContract.Presenter presenter) {
        this.Presenter = presenter;
    }
    public class TrailerHolder extends RecyclerView.ViewHolder{

        private ImageView trailerImage;
        private View itemView;
        private Context context;
        public TrailerHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.context = itemView.getContext();
            trailerImage = (ImageView) itemView.findViewById(R.id.trailer_list_item);
        }
        public void setTrailerInfo(final Youtube trailer)
        {
            String url = "https://img.youtube.com/vi/" + trailer.getSource()+ "/1.jpg";
            Picasso.with(context).load(url).resize(200, 200).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    trailerImage.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: ");

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
    }

    public class YoutubeRecyclerViewAdapter extends RecyclerView.Adapter<TrailerHolder> {

        private List<Youtube> youtubeList;
        private Context context;
        private YoutubeTrailerItemListener listener;
        public YoutubeRecyclerViewAdapter(List<Youtube> youtubeList, Context context, YoutubeTrailerItemListener listener){
            this.youtubeList = youtubeList;
            this.context = context;
            this.listener = listener;

        }
        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.trailer_item, parent, false);
            TrailerHolder holder = new TrailerHolder(view);
            return holder;
        }


        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {
            holder.setTrailerInfo(youtubeList.get(position));

        }
        public void replaceYoutubeList(List<Youtube> youtubeList)
        {
            this.youtubeList = youtubeList;
            notifyDataSetChanged();
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

            holder.setReviewInfo(reviews.get(position), position);
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
        private int position = -1;
        public ReviewHolder(View itemView, ReviewItemListener listener) {
            super(itemView);
            this.itemView = itemView;
            this.reviewItemListener = listener;

        }

        public void setReviewInfo(final Review review, int position) {
            TextView textView = (TextView) itemView.findViewById(R.id.review_author);
            textView.setText(review.getAuthor());

            TextView contentTextView = (TextView) itemView.findViewById(R.id.review_content);
            contentTextView.setMaxLines(2);
            contentTextView.setText(review.getContent());
            contentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView current = (TextView)v;
                    if(current != null)
                    {
                        int maxLines = current.getMaxLines();
                        if(maxLines == 2)
                        {
                            current.setMaxLines(Integer.MAX_VALUE);
                        }
                        else{
                            current.setMaxLines(2);
                        }
                    }
                }
            });

        }
    }

    public interface ReviewItemListener{
        void onClick(Review review);
    }
    public interface YoutubeTrailerItemListener {
        void onClick(Youtube trailer);
    }
}
