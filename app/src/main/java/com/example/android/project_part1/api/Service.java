package com.example.android.project_part1.api;

import com.example.android.project_part1.model.CreditResponse;
import com.example.android.project_part1.model.GenreResponse;
import com.example.android.project_part1.model.MovieDetail;
import com.example.android.project_part1.model.MovieResponse;
import com.example.android.project_part1.model.PeopleDetail;
import com.example.android.project_part1.model.PeopleResponse;
import com.example.android.project_part1.model.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/{priority}")
    Call<MovieResponse> getListOfMovies(@Path("priority") String priority, @Query("api_key") String apiKey, @Query("language") String language, @Query("include_adult") boolean adult);

    @GET("person/{person_id}/movie_credits")
    Call<CreditResponse> getCreditsOfPeople(@Path("person_id") int person_id, @Query("api_key") String apiKey, @Query("language") String language, @Query("include_adult") boolean adult);

    @GET("search/movie")
    Call<MovieResponse> getQuickSearchedMovies(@Query("query") String query, @Query("api_key") String apiKey,  @Query("language") String language, @Query("include_adult") boolean adult);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String language );

    @GET("movie/{movie_id}/credits")
    Call<PeopleResponse> getPeopleOverview(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("person/{people_id}")
    Call<PeopleDetail> getPeopleDetail(@Path("people_id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("genre/movie/list")
    Call<GenreResponse> getAllGenres(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("discover/movie")
    Call<MovieResponse> getDetailSearchedMovies(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("include_adult") boolean adult,
                                                @Query("primary_release_year") int year,
                                                @Query("vote_average.gte") String minimumScore,
                                                @Query("with_genres") String genre,
                                                @Query("sort_by") String sort);

}
