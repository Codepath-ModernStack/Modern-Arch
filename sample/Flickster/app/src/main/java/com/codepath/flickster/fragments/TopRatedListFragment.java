package com.codepath.flickster.fragments;

import android.util.Log;

import com.codepath.flickster.MovieRestClient;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TopRatedListFragment extends MovieListFragment {
    private static final String TAG = TopRatedListFragment.class.getSimpleName();

    public static TopRatedListFragment newInstance() {
        TopRatedListFragment fragment = new TopRatedListFragment();
        return fragment;
    }

    protected void populateMovieList() {
        // https://developers.themoviedb.org/3/movies/get-top-rated-movies
        RequestParams params = new RequestParams();
        params.put("api_key", MovieRestClient.API_KEY);
        MovieRestClient.get("movie/top_rated", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    ArrayList<Movie> movies = Movie.fromJSONArray(movieJsonResults);
                    if (movies != null) {
                        updateMovieList(movies);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Failed to fetch top rated list");
            }
        });
    }
}
