package com.example.android.project_part1.Objects;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("iso_3166_1")
    private  String iso_3166_1;

    @SerializedName("name")
    private  String name;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
