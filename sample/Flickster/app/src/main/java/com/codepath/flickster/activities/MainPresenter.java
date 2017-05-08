package com.codepath.flickster.activities;

import android.util.Log;

import com.codepath.flickster.models.MovieResponse;
import com.codepath.flickster.networking.MovieRestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by JaneChung on 5/4/17.
 */

public class MainPresenter {

    private MainScreen mainScreen;
    private MovieRestClient movieRestClient;

    public MainPresenter(MainScreen mainView, MovieRestClient client) {
        this.mainScreen = mainView;
        this.movieRestClient = client;
    }

    public void populateMovieList() {
        // https://developers.themoviedb.org/3/movies/get-now-playing
        movieRestClient.nowPlaying(new Callback<MovieResponse>(){
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.i(TAG, "onResponse");
                if(response != null) {
                    MovieResponse movieResponse = response.body();
                    if(movieResponse != null && movieResponse.getResults() != null) {
                        mainScreen.updateMovieList(movieResponse.getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }


}
