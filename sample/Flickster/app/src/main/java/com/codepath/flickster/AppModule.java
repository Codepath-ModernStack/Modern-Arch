package com.codepath.flickster;

import android.app.Application;

import com.codepath.flickster.networking.MovieRestClient;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationScope
    MovieRestClient providesMovieRestClient() {
        return new MovieRestClient();
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return mApplication;
    }
}
