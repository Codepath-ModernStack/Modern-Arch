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

public class NowPlayingListFragment extends MovieListFragment {
    private static final String TAG = NowPlayingListFragment.class.getSimpleName();

    public static NowPlayingListFragment newInstance() {
        NowPlayingListFragment fragment = new NowPlayingListFragment();
        return fragment;
    }

    protected void populateMovieList() {
        // https://developers.themoviedb.org/3/movies/get-now-playing
        RequestParams params = new RequestParams();
        params.put("api_key", MovieRestClient.API_KEY);
        MovieRestClient.get("movie/now_playing", params, new JsonHttpResponseHandler() {
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
                Log.d(TAG, "Failed to fetch now playing list");
            }
        });
    }
}
