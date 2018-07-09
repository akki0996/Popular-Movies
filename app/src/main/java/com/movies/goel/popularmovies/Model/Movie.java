package com.movies.goel.popularmovies.Model;

public class Movie {

    private String original_title;

    private String poster;
    private String overview;
    private String vote_average;

    private String release_date;

    public Movie(String original_title, String poster, String overview, String vote_average, String release_date) {
        this.original_title = original_title;
        this.poster = poster;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String get_original_title() {
        return original_title;
    }

    public String get_poster() {
        return poster;
    }

    public String get_overview() {
        return overview;
    }

    public String get_vote_average() {
        return vote_average;
    }

    public String get_release_date() {
        return release_date;
    }
}