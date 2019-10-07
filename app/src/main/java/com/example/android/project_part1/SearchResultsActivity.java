package com.example.android.project_part1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.project_part1.adapter.MovieAdapter;
import com.example.android.project_part1.api.Client;
import com.example.android.project_part1.api.Service;
import com.example.android.project_part1.model.Movie;
import com.example.android.project_part1.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView rv_search_results;
    private MovieAdapter mAdapter;
    private List<Movie> movieList;
    private String nameQuery = "";
    private String minScore;
    private int year;
    private String genre;
    private String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        rv_search_results = findViewById(R.id.rv_search_results);
        movieList = new ArrayList<>();
        mAdapter = new MovieAdapter(this, movieList, R.layout.grid_cell);
        GridLayoutManager mgridLayoutManager = new GridLayoutManager(this, 3);
        GridItemDecoration gridItemDecoration = new GridItemDecoration(3, 10, false);
        rv_search_results.setLayoutManager(mgridLayoutManager);
        rv_search_results.addItemDecoration(gridItemDecoration);
        rv_search_results.setAdapter(mAdapter);

        Intent intent = getIntent();

        if (intent.hasExtra("QUERY_NAME")) {
            nameQuery = intent.getStringExtra("QUERY_NAME");
            loadQuickSearchResults();
        } else {
            if (intent.hasExtra("SELECTED_YEAR"))
                year = Integer.valueOf(intent.getStringExtra("SELECTED_YEAR"));
            if (intent.hasExtra("ID_OF_SELECTED_GENRE"))
                genre = Integer.toString(intent.getIntExtra("ID_OF_SELECTED_GENRE", 0));

            if (intent.hasExtra("MINIMUM_SCORE")) {
                minScore = intent.getStringExtra("MINIMUM_SCORE");
            }
            if (intent.hasExtra("SELECTED_SORT")) {
                int i = intent.getIntExtra("SELECTED_SORT", 0);
                if (i == 0) {
                    sort = "popularity.desc";
                } else if (i == 1) {
                    sort = "vote_average.desc";
                } else {
                    sort = "original_title.desc";
                }

            }
            loadDetailSearchResults();
        }

    }



    private void loadQuickSearchResults(){
        Client client = new Client();
        Service apiService = client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getQuickSearchedMovies(nameQuery, BuildConfig.MY_API,MainActivity.current_language, MainActivity.current_adult);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                rv_search_results.setAdapter(new MovieAdapter(getApplicationContext(), movieList, R.layout.grid_cell));

                Log.v("MOVIE_LIST", movieList.get(0).getTitle());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDetailSearchResults(){
        Client client = new Client();
        Service apiService = client.getClient().create(Service.class);
        Call<MovieResponse> call = apiService.getDetailSearchedMovies(BuildConfig.MY_API, MainActivity.current_language,
                MainActivity.current_adult, year, minScore, genre, sort);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                rv_search_results.setAdapter(new MovieAdapter(getApplicationContext(), movieList, R.layout.grid_cell));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
