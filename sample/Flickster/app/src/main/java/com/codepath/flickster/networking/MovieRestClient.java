package com.codepath.flickster.networking;

import com.codepath.flickster.models.MovieResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRestClient {

    private final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private synchronized MovieApiInterface getService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiInterface service = retrofit.create(MovieApiInterface.class);

        return service;
    }

    // http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
    public void nowPlaying(Callback<MovieResponse> responseHandler) {
        MovieApiInterface movieApiInterface = getService();
        Call<MovieResponse> call = movieApiInterface.getNowPlayingMovies(API_KEY);
        call.enqueue(responseHandler);
    }
}
