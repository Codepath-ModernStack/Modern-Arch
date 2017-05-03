package com.codepath.flickster.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codepath.flickster.ActivityScope;
import com.codepath.flickster.ComponentProvider;
import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MoviesAdapter;
import com.codepath.flickster.databinding.ActivityMainBinding;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.views.DividerItemDecoration;
import com.codepath.flickster.views.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class MainActivity extends AppCompatActivity implements MainScreen {
    private ActivityMainBinding mainBinding;

    private List<Movie> movieList;
    private MoviesAdapter adapter;
    private RecyclerView rvMovies;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ComponentProvider) getApplicationContext())
                .applicationComponent()
                .mainActivitySubcomponent(new MainPresenterModule(this))
                .inject(this);

        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        rvMovies = mainBinding.rvMovies;
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
                        }
                    }
                });

        mPresenter.populateMovieList();
    }

    private void gotoMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void updateMovieList(List<Movie> movies) {
        movieList.addAll(movies);
        adapter.notifyDataSetChanged();
    }
}
