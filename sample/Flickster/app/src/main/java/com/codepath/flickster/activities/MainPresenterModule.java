package com.codepath.flickster.activities;

import com.codepath.flickster.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JaneChung on 3/14/17.
 */

@Module
public class MainPresenterModule {
    private final MainScreen mScreen;

    public MainPresenterModule(MainScreen screen) {
        mScreen = screen;
    }

    @Provides
    @ActivityScope
    MainScreen providesMainScreen() {
        return mScreen;
    }
}
