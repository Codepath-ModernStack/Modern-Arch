package com.codepath.flickster.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.codepath.flickster.R;
import com.codepath.flickster.databinding.ActivityDetailsBinding;
import com.codepath.flickster.models.Movie;

public class MovieDetailsActivity extends Activity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    ActivityDetailsBinding detailsBinding;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        movie = getIntent().getParcelableExtra("movie");
        detailsBinding.setMovie(movie);
    }
}
