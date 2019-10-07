package com.example.android.project_part1;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_part1.adapter.MovieAdapter;
import com.example.android.project_part1.adapter.PeopleAdapter;
import com.example.android.project_part1.api.Client;
import com.example.android.project_part1.api.Service;
import com.example.android.project_part1.model.CreditResponse;
import com.example.android.project_part1.model.Movie;
import com.example.android.project_part1.model.MovieResponse;
import com.example.android.project_part1.model.People;
import com.example.android.project_part1.model.PeopleDetail;
import com.example.android.project_part1.model.PeopleResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleDetailActivity extends AppCompatActivity{
    ProgressDialog pd;
    private TextView peopleName;
    private TextView birthDate;
    private TextView birthPlace;
    private TextView short_bio;
    private ImageView peoplePoster;
    private RecyclerView rvMoviesOfPeople;

    private MovieAdapter mAdapter;
    private List<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
        initializeViews();

    }

    private void initializeViews(){

        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.fetch_movies));
        pd.setCancelable(false);
        pd.show();

        peopleName = findViewById(R.id.peopleName);
        birthDate = findViewById(R.id.birthDay);
        birthPlace = findViewById(R.id.birthPlace);
        short_bio = findViewById(R.id.short_bio);
        peoplePoster = findViewById(R.id.peoplePoster);
        rvMoviesOfPeople = findViewById(R.id.rvMoviesOfPeople);

        movieList = new ArrayList<>();
        mAdapter = new MovieAdapter(this, movieList, R.layout.movies_of_people_cell);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMoviesOfPeople.setAdapter(mAdapter);
        rvMoviesOfPeople.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rvMoviesOfPeople.setLayoutManager(linearLayoutManager);

        int person_id = getIntent().getIntExtra("PEOPLE_ID",0);
        loadJson(person_id);

    }

    private void loadJson(int person_id){
        try{
            if(BuildConfig.MY_API.isEmpty()){
                Toast.makeText(getApplicationContext(), "There is no API key", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;

            }
            Client client = new Client();
            Service apiService = client.getClient().create(Service.class);
            Call<PeopleDetail> call = apiService.getPeopleDetail(person_id, BuildConfig.MY_API, MainActivity.current_language);
            call.enqueue(new Callback<PeopleDetail>() {
                @Override
                public void onResponse(Call<PeopleDetail> call, Response<PeopleDetail> response) {
                    PeopleDetail peopleDetail = response.body();
                    peopleName.setText(peopleDetail.getName());
                    birthDate.setText(peopleDetail.getBirthday());
                    birthPlace.setText(peopleDetail.getPlace_of_birth());
                    short_bio.setText(peopleDetail.getBiography());

                    Picasso.get().load(peopleDetail.getProfile_path()).into(peoplePoster);



                }

                @Override
                public void onFailure(Call<PeopleDetail> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),  getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
                }


            });

            Call<CreditResponse> calll = apiService.getCreditsOfPeople(person_id, BuildConfig.MY_API, MainActivity.current_language, MainActivity.current_adult);
            calll.enqueue(new Callback<CreditResponse>() {
                @Override
                public void onResponse(Call<CreditResponse> calll, Response<CreditResponse> response) {
                    List<Movie> movies = response.body().getCast();
                    rvMoviesOfPeople.setAdapter(new MovieAdapter(getApplicationContext(), movies, R.layout.movies_of_people_cell));
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<CreditResponse> call, Throwable t) {

                }


            });

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),  getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
        }

    }

    public void clickLogoEvent(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
