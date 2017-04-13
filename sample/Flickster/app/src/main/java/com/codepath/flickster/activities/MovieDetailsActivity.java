package com.codepath.flickster.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends Activity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @BindView(R.id.ivBackdrop) ImageView ivBackdrop;
    @BindView(R.id.tvTitle) TextView tvTitle;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        if (movie != null) {
            initView();
        } else {
            Log.e(TAG, "Movie is NULL");
        }
    }

    private void initView() {
        Picasso.with(this).load(movie.getBackdropImagePath()).into(ivBackdrop);
        tvTitle.setText(movie.getOriginalTitle());
    }
}
