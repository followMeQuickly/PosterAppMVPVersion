package com.example.chaseland.moviepostermvp.posters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.data.Poster;
import com.example.chaseland.moviepostermvp.posterDetail.PosterDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaseland on 1/12/17.
 */

public class PostersFragment extends Fragment implements PostersContract.PosterView {

    private PostersContract.Presenter Presenter;

    private PosterRecyclerAdapter PosterRecyclerAdapter;

    PosterItemListener PosterItemListener = new PosterItemListener() {
        @Override
        public void onClick(Poster poster) {
            Presenter.OpenPosterDetails(poster);
        }
    };


    public static PostersFragment newInstance() {

    Bundle args = new Bundle();
    PostersFragment fragment = new PostersFragment();
    fragment.setArguments(args);
    return fragment;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.PosterRecyclerAdapter = new PosterRecyclerAdapter(new ArrayList<Poster>(0), PosterItemListener, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_posters, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.gridview);

        recyclerView.setHasFixedSize(true);

        int tilePadding = 8;
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(PosterRecyclerAdapter);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Presenter.Start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // todo: replace with variable. One entry and one exit from the function. ONE RETURN
        if(item.getItemId() == R.id.popular_posters){
            Presenter.changeFilterType(PosterFilterType.POPULAR);
            Presenter.loadPosters(true);
            return true;
        } else if (item.getItemId() == R.id.highest_rated_posters) {
            Presenter.changeFilterType(PosterFilterType.HIGHEST_RATED);
            Presenter.loadPosters(true);
            return true;
        }
        return false;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Presenter.result(requestCode, resultCode);
    }

    @Override
    public void showPosters(List<Poster> posters) {

        this.PosterRecyclerAdapter.replacePosters(posters);
    }

    @Override
    public void showFilteringPopMenu() {


    }

    @Override
    public void showPosterDetailsUI(String posterId) {

        Intent intent = new Intent(getContext(), PosterDetailActivity.class);
        intent.putExtra(PosterDetailActivity.TASK_DETAIL_ID, posterId);
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showFavoritedPosters() {

    }

    @Override
    public void showPostersLoadingError() {

    }

    @Override
    public boolean isActive() {
        return false;
    }


    @Override
    public void setPresenter(PostersContract.Presenter presenter) {
        this.Presenter = presenter;

    }

    public class PosterRecyclerAdapter extends RecyclerView.Adapter<PosterHolder>{

        private List<Poster> posters;

        private PosterItemListener posterItemListener;

        private Context context;
        public PosterRecyclerAdapter(List<Poster> posters, PosterItemListener posterItemListener, Context context) {
            this.posters = posters;
            this.posterItemListener = posterItemListener;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return posters.size();
        }

        @Override
        public PosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater =  LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.poster_item, parent, false);
            PosterHolder posterHolder = new PosterHolder(view, posterItemListener);
            return posterHolder;
        }

        @Override
        public void onBindViewHolder(PosterHolder holder, int position) {
            holder.setPosterInfo(posters.get(position));

        }

        public void replacePosters(List<Poster> posters){
            this.posters = posters;
            notifyDataSetChanged();
        }
    }


    public class PosterHolder extends RecyclerView.ViewHolder{
        private View itemView;

        private PosterItemListener posterItemListener;
        public PosterHolder(View itemView, PosterItemListener posterItemListener) {
            super(itemView);
            this.itemView = itemView;
            this.posterItemListener = posterItemListener;
        }

        public void setPosterInfo(final Poster poster) {

            ImageView imageView = (ImageView) itemView.findViewById(R.id.poster_image);
            Picasso.with(itemView.getContext()).load(poster.getImagePath()).into(imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posterItemListener.onClick(poster);
                }

            });

        }
    }

    public interface PosterItemListener{
        void onClick(Poster poster);

    }

}
