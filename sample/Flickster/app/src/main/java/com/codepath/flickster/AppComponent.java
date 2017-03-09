package com.codepath.flickster;

import com.codepath.flickster.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(MainActivity activity);
}
