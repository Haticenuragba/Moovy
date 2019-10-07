package com.example.android.project_part1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PeopleResponse{
@SerializedName("cast")
List<People> cast;


public List<People> getCast() {
        return cast;
        }

public void setCast(List<People> cast) {
        this.cast = cast;
        }


}
