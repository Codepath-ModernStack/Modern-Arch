package com.codepath.flickster.networking;

import com.codepath.flickster.MovieApp;
import com.codepath.flickster.models.MovieResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

public class MovieRestClient {

    private final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Inject public MovieApiInterface apiInterface;

    public MovieRestClient() {
        MovieApp.getApp().getAppComponent().inject(this);
    }

    // http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
    public void nowPlaying(Callback<MovieResponse> responseHandler) {
        Call<MovieResponse> call = apiInterface.getNowPlayingMovies(API_KEY);
        call.enqueue(responseHandler);
    }
}
