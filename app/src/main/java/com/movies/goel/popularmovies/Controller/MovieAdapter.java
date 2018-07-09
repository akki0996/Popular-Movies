
package com.movies.goel.popularmovies.Controller;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.movies.goel.popularmovies.Model.Movie;
import com.movies.goel.popularmovies.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private Context mContext;
    private List<Movie> mMovieList;
    private String TAG = "MovieRecycleViewAdapter";

    private final MovieAdapterOnClickHandler mClickHandler;



    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(Context mContext, List<Movie> movie_list, MovieAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mMovieList = movie_list;
        this.mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieAdapterViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder theHolder, final int position) {
        final Movie movie = mMovieList.get(position);
        Glide.with(mContext).load(movie.get_poster()).into(theHolder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView thumbnail;

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Movie movie = mMovieList.get(index);

            mClickHandler.onClick(movie);


        }

        public MovieAdapterViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }
    }
}
