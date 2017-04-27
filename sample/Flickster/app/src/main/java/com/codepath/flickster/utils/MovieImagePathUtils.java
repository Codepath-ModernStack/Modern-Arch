package com.codepath.flickster.utils;

import com.codepath.flickster.models.Movie;

public class MovieImagePathUtils {
    private static final String BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/";

    public static String getBackdropImagePath(Movie movie) {
        return String.format("%sw1280/%s", BASE_IMAGE_PATH, movie.getBackdropPath());
    }

    public static String getPosterImagePath(Movie movie) {
        return String.format("%sw342/%s", BASE_IMAGE_PATH, movie.getPosterPath());
    }
}
