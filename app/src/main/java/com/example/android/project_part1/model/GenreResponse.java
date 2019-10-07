package com.example.android.project_part1.model;

import com.example.android.project_part1.Objects.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @SerializedName("genres")
    private List<Genre> genres;
}
