package com.codepath.flickster.networking;

import com.codepath.flickster.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface AppComponent {
    // We need these for every Activity/Fragment/View/Object that has a
    // variable with the @Inject annotation

    // We need this for @Inject MovieRestClient inside MainActivity.java
    void inject(MainActivity activity);

    // We need this for @Inject MovieApiInterface in MovieRestClient.java
    void inject(MovieRestClient client);
}
