package com.codepath.flickster;

import android.app.Application;
import android.content.Context;

import com.codepath.flickster.networking.AppComponent;
import com.codepath.flickster.networking.AppModule;
import com.codepath.flickster.networking.DaggerAppComponent;
import com.codepath.flickster.networking.NetModule;

public class MovieApp extends Application {

    private AppComponent mAppComponent;
    private static Context context;

    public MovieApp() {
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        mAppComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule("http://api.themoviedb.org/3/"))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
    }

    public static Object getAppContext() {
        return context;
    }

    // We need this to inject with dagger inside custom objects
    public static MovieApp getApp() {
        return (MovieApp) getAppContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
