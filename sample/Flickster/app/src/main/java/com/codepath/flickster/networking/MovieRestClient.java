package com.codepath.flickster.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MovieRestClient {
    private final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final String BASE_URL = "http://api.themoviedb.org/3/";

    private AsyncHttpClient client;

    public MovieRestClient() {
        client = new AsyncHttpClient();
    }

    public void nowPlaying(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(getAbsoluteUrl("movie/now_playing"), params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put("api_key", API_KEY);
        client.get(url, params, responseHandler);
    }
}
