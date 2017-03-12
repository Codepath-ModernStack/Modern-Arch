package com.codepath.flickster.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.MovieResponse;
import com.codepath.flickster.networking.MovieRestClient;
import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.databinding.ActivityMainBinding;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.views.DividerItemDecoration;
import com.codepath.flickster.views.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        movieRestClient.nowPlaying(new Callback<MovieResponse>(){
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.i(TAG, "onResponse");
                if(response != null) {
                    MovieResponse movieResponse = response.body();
                    if(movieResponse != null && movieResponse.getResults() != null) {
                        updateMovieList(movieResponse.getResults());
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
