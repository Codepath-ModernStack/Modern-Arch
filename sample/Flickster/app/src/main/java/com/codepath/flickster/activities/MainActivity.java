package com.codepath.flickster.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.flickster.networking.MovieRestClient;
import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.databinding.ActivityMainBinding;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.views.DividerItemDecoration;
import com.codepath.flickster.views.ItemClickSupport;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mainBinding;

    private MovieRestClient movieRestClient;
    private List<Movie> movieList;
    private MoviesAdapter adapter;
    private RecyclerView rvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        rvMovies = mainBinding.rvMovies;
        movieRestClient = new MovieRestClient();
        initMovieList();
    }

    private void initMovieList() {
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvMovies.addItemDecoration(itemDecoration);

        ItemClickSupport.addTo(rvMovies).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Movie movie = movieList.get(position);
                        if (movie != null) {
                            gotoMovieDetails(movie);
                        } else {
                            Log.e(TAG, "Movie is NULL");
                        }
                    }
                });

        populateMovieList();
    }

    private void gotoMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    protected void updateMovieList(List<Movie> movies) {
        movieList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    protected void populateMovieList() {
        // https://developers.themoviedb.org/3/movies/get-now-playing
        RequestParams params = new RequestParams();
        movieRestClient.nowPlaying(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults;
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
