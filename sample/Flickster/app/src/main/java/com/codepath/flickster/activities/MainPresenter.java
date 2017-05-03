package com.codepath.flickster.activities;

import android.util.Log;

import com.codepath.flickster.ActivityScope;
import com.codepath.flickster.models.MovieResponse;
import com.codepath.flickster.networking.MovieRestClient;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JaneChung on 3/14/17.
 */

@ActivityScope
public class MainPresenter {
    private final MovieRestClient mClient;
    private MainScreen mScreen;
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter(final MovieRestClient client, final MainScreen screen) {
        mClient = client;
        mScreen = screen;
    }

    public void populateMovieList() {

        // https://developers.themoviedb.org/3/movies/get-now-playing
        mClient.nowPlaying(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.i(TAG, "onResponse");
                if(response != null) {
                    MovieResponse movieResponse = response.body();
                    if(movieResponse != null && movieResponse.getResults() != null) {
                        mScreen.updateMovieList(movieResponse.getResults());
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
