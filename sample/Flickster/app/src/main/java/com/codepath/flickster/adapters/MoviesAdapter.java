package com.codepath.flickster.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.databinding.ItemMovieBinding;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.utils.MovieImagePathUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    public static final String TAG = MoviesAdapter.class.getSimpleName();

    private Context context;
    private List<Movie> movieList;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding movieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_movie,parent,false);
        ViewHolder viewHolder = new ViewHolder(movieBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (movie != null) {
            Picasso.with(context).load(MovieImagePathUtils.getPosterImagePath(movie)).into(holder.ivPoster);
            holder.tvTitle.setText(movie.getOriginalTitle());
            holder.tvDescription.setText(movie.getOverview());
        } else {
            Log.e(TAG, "Movie is NULL");
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvDescription;

        ViewHolder(ItemMovieBinding movieBinding) {
            super(movieBinding.getRoot());
            ivPoster = movieBinding.ivPoster;
            tvTitle = movieBinding.tvTitle;
            tvDescription = movieBinding.tvDescription;
        }
    }
}
