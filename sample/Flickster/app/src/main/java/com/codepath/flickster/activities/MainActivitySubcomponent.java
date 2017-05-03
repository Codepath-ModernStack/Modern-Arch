package com.codepath.flickster.activities;

import com.codepath.flickster.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by JaneChung on 3/14/17.
 */

@Subcomponent(modules = {MainPresenterModule.class})
@ActivityScope
public interface MainActivitySubcomponent {
    void inject(MainActivity activity);
}
