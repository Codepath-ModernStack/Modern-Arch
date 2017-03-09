package com.codepath.flickster.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MoviesPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.vpMovies)
    ViewPager vpMovies;
    @BindView(R.id.tbTitles)
    TabLayout tbTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        vpMovies.setAdapter(new MoviesPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        tbTitles.setupWithViewPager(vpMovies);
    }
}
