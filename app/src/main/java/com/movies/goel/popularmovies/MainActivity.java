package com.movies.goel.popularmovies;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import android.util.Log;
import android.util.TypedValue;


import com.movies.goel.popularmovies.Controller.MovieAdapter;
import com.movies.goel.popularmovies.JSONParser.JSONMovieParser;
import com.movies.goel.popularmovies.Model.GridDecoration;
import com.movies.goel.popularmovies.Model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.ApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        List<Movie> mMovieList = new ArrayList<>();

        MovieAdapter mAdapter = new MovieAdapter(this, mMovieList, this);


        int numOfColumns = calculateNoOfColumns();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, numOfColumns);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.addItemDecoration(new GridDecoration(2, dpToPx(), true));
       // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        JSONMovieParser mJsonParserMovies = new JSONMovieParser(mMovieList, url);
        mJsonParserMovies.processInfo(mJsonParserMovies);

        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(Movie movie) {
        Log.e("Hello", "Hello");
        Log.e("Movie Title", movie.get_original_title());

    }



    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics()));
    }


    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

}
