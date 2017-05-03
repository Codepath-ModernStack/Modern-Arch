package com.codepath.flickster;

import com.codepath.flickster.activities.MainActivitySubcomponent;
import com.codepath.flickster.activities.MainPresenterModule;

import dagger.Component;

/**
 * Created by JaneChung on 3/13/17.
 */

@ApplicationScope
@Component(modules={AppModule.class})
public interface AppComponent {
    void inject(Flickster flickster);
    MainActivitySubcomponent mainActivitySubcomponent(MainPresenterModule module);
}
