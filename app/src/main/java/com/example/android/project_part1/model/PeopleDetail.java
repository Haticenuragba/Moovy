package com.example.android.project_part1.model;

import com.example.android.project_part1.Objects.Country;
import com.example.android.project_part1.Objects.Genre;
import com.google.gson.annotations.SerializedName;

public class PeopleDetail {
    @SerializedName("birthday")
    private  String birthday;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("biography")
    private String biography;

    @SerializedName("place_of_birth")
    private String place_of_birth;

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("known_for_department")
    private String known_for_department;

    final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";


    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return BASE_IMAGE_URL + profile_path;
    }

    public void setProfile_path(String profile_path) {

    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }
}
