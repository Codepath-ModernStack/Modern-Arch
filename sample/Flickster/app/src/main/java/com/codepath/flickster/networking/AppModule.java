package com.codepath.flickster.networking;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    MovieRestClient providesMovieRestClient() {
        return new MovieRestClient();
    }
}
