package com.example.android.project_part1.model;

import com.google.gson.annotations.SerializedName;

public class People {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("profile_path")
    private String profile_path;
    final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";



    public People(int id, String name, String profile_path, String credit_id) {
        this.id = id;

        this.name = name;
        this.profile_path = profile_path;
        this.credit_id = credit_id;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return BASE_IMAGE_URL + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }
}