package com.example.android.project_part1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity{

    public static String current_language;
    public static boolean current_adult;
    public static boolean current_original_title;





    private RecyclerView rv_main;
    private MovieAdapter mAdapter;
    private List<Movie> movieList;
    ProgressDialog pd;
    public static String priority;
    public static final String LOG_TAG =  MovieAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences language_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        current_language = language_prefs.getString("language", "en-US");

        SharedPreferences adult_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        current_adult = adult_prefs.getBoolean("adult_content", false);

        SharedPreferences original_title_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        current_original_title = original_title_prefs.getBoolean("original_title", false);




        initializeViews();

        rv_main.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {

            }
        });

    }

    private void initializeViews(){
        priority = "top_rated";
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.fetch_movies));
        pd.setCancelable(false);
        pd.show();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        movieList = new ArrayList<>();
        mAdapter = new MovieAdapter(this, movieList, R.layout.grid_cell);
        if(getActivity().getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            GridLayoutManager mgridLayoutManager   = new GridLayoutManager(this, 3);
            GridItemDecoration gridItemDecoration = new GridItemDecoration(3, 10, false);
            rv_main.setLayoutManager(mgridLayoutManager);
            rv_main.addItemDecoration(gridItemDecoration);
        }
        else{
            GridLayoutManager mgridLayoutManager   = new GridLayoutManager(this, 5);
            GridItemDecoration gridItemDecoration = new GridItemDecoration(5, 10, false);
            rv_main.setLayoutManager(mgridLayoutManager);
            rv_main.addItemDecoration(gridItemDecoration);
        }
        rv_main.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        loadJson(priority);

    }

    private void loadJson(String priority){

            try {
                if (BuildConfig.MY_API.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "There is no API key", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    return;

                }
                Client client = new Client();
                Service apiService = client.getClient().create(Service.class);
                Call<MovieResponse> call = apiService.getListOfMovies(priority, BuildConfig.MY_API, current_language, current_adult);
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        movieList = response.body().getResults();
                        pd.dismiss();
                        rv_main.setAdapter(new MovieAdapter(getApplicationContext(), movieList, R.layout.grid_cell));
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
            }
        }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Context context = getApplicationContext();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.action_favourites){
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.action_search){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    movieList = new ArrayList<>();
                    loadJson("top_rated");
                    priority = "top_rated";
                    mAdapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_dashboard:
                    movieList = new ArrayList<>();
                    loadJson("now_playing");
                    priority = "now_playing";
                    mAdapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_notifications:
                    movieList = new ArrayList<>();
                    loadJson("upcoming");
                    priority = "upcoming";
                    mAdapter.notifyDataSetChanged();
                    return true;
            }
            return false;
        }
    };

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }



    protected void onRestart() {
        super.onRestart();
        movieList = new ArrayList<>();
        loadJson(priority);
    }


}
