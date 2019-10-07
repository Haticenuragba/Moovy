package com.example.android.project_part1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {



    @SerializedName("cast")
    List<Movie> cast;


    public List<Movie> getCast() {
        return cast;
    }

    public void setCast(List<Movie> cast) {
        this.cast = cast;
    }
}
