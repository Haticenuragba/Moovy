package com.example.android.project_part1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_part1.Objects.Genre;
import com.example.android.project_part1.adapter.MovieAdapter;
import com.example.android.project_part1.api.Client;
import com.example.android.project_part1.api.Service;
import com.example.android.project_part1.model.GenreResponse;
import com.example.android.project_part1.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText nameOfMovieSearch;
    EditText yearOfMovieSearch;
    EditText minimumScore;
    Spinner spinnerOfGenre;
    Spinner spinnerOfSort;
    Button buttonOfQuickSearch;
    Button buttonOfDetailedSearch;
    List<Genre> listOfGenres;
    List<String> nameOfGenres;
    String[] nameOfSorts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeViews();
    }

    private void initializeViews(){
        nameOfMovieSearch = findViewById(R.id.nameOfMovieSearch);
        yearOfMovieSearch = findViewById(R.id.yearOfMovieSearch);
        minimumScore = findViewById(R.id.minimumScore);
        spinnerOfGenre = findViewById(R.id.spinnerOfGenre);
        spinnerOfSort = findViewById(R.id.spinnerOfSort);
        buttonOfDetailedSearch = findViewById(R.id.detailedSearchButton);
        buttonOfQuickSearch = findViewById(R.id.quickSearchButton);
        nameOfSorts = getResources().getStringArray(R.array.sort_types);
        ArrayAdapter adapter =  new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,  nameOfSorts);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerOfSort.setAdapter(adapter);
        populateGenreList();



        buttonOfQuickSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameOfMovieSearch.getText().toString().matches("")){
                    Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    Log.v("QUERY", nameOfMovieSearch.getText().toString());
                    intent.putExtra("QUERY_NAME", nameOfMovieSearch.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "You should enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOfDetailedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);

                if(!yearOfMovieSearch.getText().toString().matches("")){
                    intent.putExtra("SELECTED_YEAR", yearOfMovieSearch.getText().toString());
                }
                if(!minimumScore.getText().toString().matches("")){
                    intent.putExtra("MINIMUM_SCORE", minimumScore.getText().toString());
                }
                if(!spinnerOfGenre.getSelectedItem().toString().matches("")){
                    intent.putExtra("ID_OF_SELECTED_GENRE", listOfGenres.get(spinnerOfGenre.getSelectedItemPosition()).getId());
                }
                if(!spinnerOfSort.getSelectedItem().toString().matches("")){
                    intent.putExtra("SELECTED_SORT", spinnerOfSort.getSelectedItemPosition());
                }
                startActivity(intent);
            }
        });





    }

    private void populateGenreList(){
        Client client = new Client();
        Service apiService = client.getClient().create(Service.class);
        Call<GenreResponse> call = apiService.getAllGenres(BuildConfig.MY_API, MainActivity.current_language);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                listOfGenres = response.body().getGenres();
                nameOfGenres = new ArrayList<String>();
                for(int i=0; i<listOfGenres.size(); i++){
                    nameOfGenres.add(i, listOfGenres.get(i).getName());
                }
                ArrayAdapter adapter =  new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,  nameOfGenres);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinnerOfGenre.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Make sure of your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
