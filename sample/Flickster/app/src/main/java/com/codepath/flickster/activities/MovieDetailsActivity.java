package com.codepath.flickster.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.databinding.ActivityDetailsBinding;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends Activity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    ActivityDetailsBinding detailsBinding;
    ImageView ivBackdrop;
    TextView tvTitle;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        ivBackdrop = detailsBinding.ivBackdrop;
        tvTitle = detailsBinding.tvTitle;
        movie = (Movie) getIntent().getSerializableExtra("movie");
        if (movie != null) {
            initView();
        } else {
            Log.e(TAG, "Movie is NULL");
        }
    }

    private void initView() {
        Picasso.with(this).load(movie.getBackdropPath()).into(ivBackdrop);
        tvTitle.setText(movie.getOriginalTitle());
    }
}
