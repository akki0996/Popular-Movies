package com.movies.goel.popularmovies.JSONParser;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.movies.goel.popularmovies.API_CALLS.AsyncGetRequest;
import com.movies.goel.popularmovies.Model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class JSONMovieParser {

    private List<Movie> movie_list;
    private String resultStringFromServer = " ";
    private String myURL;

    private String TAG = "JsonParserMovies";


    public JSONMovieParser(List<Movie> theMovieList, String theURL) {
        movie_list = theMovieList;
        myURL = theURL;
    }


    public String getResultFromServer() {
        AsyncGetRequest getRequest = new AsyncGetRequest();
        try {
            resultStringFromServer = getRequest.execute(myURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultStringFromServer;
    }


    public boolean hasErrorCode(String serverString) {
        return serverString.equals("Authentication Failed") || serverString.equals("Invalid Parameters")
                || serverString.equals("Request Reached Limit") || serverString.equals("Server Timed Out")
                || serverString.equals("Unknown Error");
    }

    public JSONArray getJsonArray(String theJsonString) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(theJsonString);
            Log.e(TAG, theJsonString);
            String movieResult = jsonObject.getString("results");
            jsonArray = new JSONArray(movieResult);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void parseJsonFromString(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);

                String movie_title = jsonPart.getString("original_title");
                String movie_poster = "http://image.tmdb.org/t/p/w500/" + jsonPart.getString("poster_path");
                String movie_overview = jsonPart.getString("overview");
                String vote_average = jsonPart.getString("overview");
                String release_date = jsonPart.getString("release_date");

                Movie new_movie = new Movie(movie_title, movie_poster, movie_overview, vote_average, release_date);

                movie_list.add(new_movie);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void processInfo(JSONMovieParser theJsonParser) {
        String theResult = theJsonParser.getResultFromServer();
        if (!theJsonParser.hasErrorCode(theResult)) {
            JSONArray tempJsonArray = theJsonParser.getJsonArray(theResult);
            if (tempJsonArray.length() > 0) {
                theJsonParser.parseJsonFromString(tempJsonArray);
            }
        }
    }
}
