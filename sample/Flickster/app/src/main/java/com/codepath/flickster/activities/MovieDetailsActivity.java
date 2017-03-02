package com.codepath.flickster.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flickster.MovieRestClient;
import com.codepath.flickster.R;
import com.codepath.flickster.models.GuestSession;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends Activity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @BindView(R.id.ivBackdrop)
    ImageView ivBackdrop;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.btRating)
    Button btRating;

    private Movie movie;
    private GuestSession guestSession;

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
        Picasso.with(this).load(movie.getBackdropPath()).into(ivBackdrop);
        tvTitle.setText(movie.getOriginalTitle());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                postMovieRating(rating);
            }
        });
    }

    @OnClick(R.id.btRating)
    public void rateMovie(View view) {
        if (guestSession == null) {
            showGuestSessionPrompt();
        } else {
            showRatingBar();
        }
    }

    private void showGuestSessionPrompt() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.rate_movie)
                .setMessage(R.string.guest_session_prompt)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        createGuestSession();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showRatingBar() {
        ratingBar.setVisibility(View.VISIBLE);
        btRating.setVisibility(View.GONE);
    }

    private void createGuestSession() {
        // https://developers.themoviedb.org/3/authentication/create-guest-session
        RequestParams params = new RequestParams();
        params.put("api_key", MovieRestClient.API_KEY);
        MovieRestClient.get("authentication/guest_session/new", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    guestSession = new GuestSession(response);
                    if (guestSession != null) {
                        showRatingBar();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }

    // https://developers.themoviedb.org/3/movies/rate-movie
    private void postMovieRating(float rating) {
        String url = String.format("movie/%s/rating", movie.getId());
        RequestParams params = new RequestParams();
        params.put("api_key", MovieRestClient.API_KEY);
        params.put("guest_session_id", guestSession.getSessionId());
        params.put("value", rating * 2);
        MovieRestClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showToast("Success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToast("Failed");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
