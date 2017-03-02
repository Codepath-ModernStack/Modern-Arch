package com.codepath.flickster.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.flickster.R;
import com.codepath.flickster.activities.MovieDetailsActivity;
import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.views.DividerItemDecoration;
import com.codepath.flickster.views.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MovieListFragment extends Fragment {
    private static final String TAG = MovieListFragment.class.getSimpleName();

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

    private Context mContext;
    private List<Movie> movieList;
    private MoviesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mContext = getActivity();
        initMovieList();
    }

    private void initMovieList() {
        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(mContext, movieList);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
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
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    protected void updateMovieList(List<Movie> movies) {
        movieList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    protected abstract void populateMovieList();
}
