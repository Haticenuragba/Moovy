package com.example.android.project_part1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.project_part1.adapter.MovieAdapter;
import com.example.android.project_part1.adapter.PeopleAdapter;
import com.example.android.project_part1.adapter.ReviewAdapter;
import com.example.android.project_part1.api.Client;
import com.example.android.project_part1.api.Service;
import com.example.android.project_part1.model.Movie;
import com.example.android.project_part1.model.MovieDetail;
import com.example.android.project_part1.model.MovieResponse;
import com.example.android.project_part1.model.People;
import com.example.android.project_part1.model.PeopleResponse;
import com.example.android.project_part1.model.Review;
import com.example.android.project_part1.model.ReviewResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity{
    private TextView movieTitleDetail;
    private TextView releaseDate;
    private TextView location;
    private TextView storyLine;
    private TextView movieRating;
    private TextView isThereReview;
    private TextView tagLine;
    private TextView movieGenres;
    private TextView movieLargeTitle;
    private ImageView largePoster;
    private ImageView smallPoster;
    ProgressDialog pd;

    private RecyclerView rv_of_cast;
    private PeopleAdapter mAdapter;
    private List<People> castList;

    private List<Review> reviewList;
    private RecyclerView rv_of_review;
    private ReviewAdapter rAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initializeViews();




    }

    private void initializeViews(){
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.fetch_movies));
        pd.setCancelable(false);
        pd.show();
        movieTitleDetail = findViewById(R.id.movieTitleDetail);
        releaseDate = findViewById(R.id.releaseDate);
        location = findViewById(R.id.location);
        storyLine = findViewById(R.id.storyLine);
        movieRating = findViewById(R.id.movieRating);
        isThereReview = findViewById(R.id.isThereReview);
        isThereReview.setVisibility(View.INVISIBLE);
        tagLine = findViewById(R.id.tagLine);
        movieGenres = findViewById(R.id.movieGenres);
        movieLargeTitle = findViewById(R.id.movieLargeTitle);
        largePoster = findViewById(R.id.largePoster);
        smallPoster = findViewById(R.id.smallPoster);
        int movieID = getIntent().getIntExtra("MOVIE_ID",0);

        rv_of_cast = findViewById(R.id.rvCastOfMovie);
        castList = new ArrayList<>();
        mAdapter = new PeopleAdapter(this, castList);
        LinearLayoutManager peopleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_of_cast.setAdapter(mAdapter);
        rv_of_cast.addItemDecoration(new DividerItemDecoration(this, peopleLayoutManager.getOrientation()));
        rv_of_cast.setLayoutManager(peopleLayoutManager);


        rv_of_review = findViewById(R.id.rvReviewsOfMovie);
        reviewList = new ArrayList<>();
        rAdapter = new ReviewAdapter(this, reviewList);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_of_review.setAdapter(mAdapter);
        rv_of_review.addItemDecoration(new DividerItemDecoration(this, reviewLayoutManager.getOrientation()));
        rv_of_review.setLayoutManager(reviewLayoutManager);

        loadJson(movieID);

    }

    private void loadJson(int id){
        try{
            if(BuildConfig.MY_API.isEmpty()){
                Toast.makeText(getApplicationContext(), "There is no API key", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;

            }
            Client client = new Client();
            Service apiService = client.getClient().create(Service.class);
            Call<MovieDetail> call = apiService.getMovieDetails(id, BuildConfig.MY_API, MainActivity.current_language);
            call.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                    MovieDetail movie_detail = response.body();
                    movieTitleDetail.setText(movie_detail.getTitle());
                    movieLargeTitle.setText(movie_detail.getTitle());
                    releaseDate.setText(movie_detail.getRelease_date());
                    location.setText(movie_detail.getProduction_countries());
                    storyLine.setText(movie_detail.getOverview());
                    movieRating.setText(movie_detail.getVote_average());
                    tagLine.setText(movie_detail.getTagline());
                    movieGenres.setText(movie_detail.getGenres());
                    Picasso.get().load(movie_detail.getPoster_path()).into(largePoster);
                    Picasso.get().load(movie_detail.getPoster_path()).into(smallPoster);

                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
                }
            });

            Call<PeopleResponse> calll = apiService.getPeopleOverview(id, BuildConfig.MY_API, MainActivity.current_language);
            calll.enqueue(new Callback<PeopleResponse>() {
                @Override
                public void onResponse(Call<PeopleResponse> calll, Response<PeopleResponse> response) {
                    List<People> cast = response.body().getCast();
                    rv_of_cast.setAdapter(new PeopleAdapter(getApplicationContext(), cast));

                }

                @Override
                public void onFailure(Call<PeopleResponse> call, Throwable t) {

                }


            });

            Call<ReviewResponse> callll = apiService.getReviews(id, BuildConfig.MY_API, MainActivity.current_language);
            callll.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> callll, Response<ReviewResponse> response) {
                    List<Review> reviews = response.body().getResults();
                    if(reviews.isEmpty()){
                        isThereReview.setVisibility(View.VISIBLE);
                    }
                    else{
                        isThereReview.setVisibility(View.INVISIBLE);
                    }
                    rv_of_review.setAdapter(new ReviewAdapter(getApplicationContext(), reviews));
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {

                }


            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_internet), Toast.LENGTH_SHORT).show();
        }

    }
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

}