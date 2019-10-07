package com.example.android.project_part1.model;

import com.example.android.project_part1.Objects.Country;
import com.example.android.project_part1.Objects.Genre;
import com.google.gson.annotations.SerializedName;

public class MovieDetail {

    @SerializedName("adult")
    private  boolean adult;

    @SerializedName("genres")
    private Genre[] genres;

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("production_countries")
    private Country[] production_countries;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private String vote_average;

    public String getTagline() {
        return "'" + tagline + "'";
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @SerializedName("tagline")
    private String tagline;

    final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getGenres() {
        String genre_string = "";
        for(int i=0; i<genres.length;i++){
            if(i!=genres.length-1) {
                genre_string = genre_string + genres[i].getName() + " / ";
            }
            else{
                genre_string = genre_string + genres[i].getName();
            }
        }
        return genre_string;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return BASE_IMAGE_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getProduction_countries() {
        if(production_countries.length!=0){
            return production_countries[0].getName();
        }
      else{
            return "";
        }
    }

    public void setProduction_countries(Country[] production_countries) {
        this.production_countries = production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}
