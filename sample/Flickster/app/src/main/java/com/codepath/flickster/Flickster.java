package com.codepath.flickster;

import android.app.Application;


public class Flickster extends Application implements ComponentProvider {

    private AppComponent mComponent;

    @Override
    public AppComponent applicationComponent() {
        return mComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mComponent.inject(this);
    }
}
