package com.example.app;

import android.app.Application;

import com.example.app.dagger.AndroidModule;
import com.example.app.dagger.DataModule;
import com.example.app.dagger.DomainModule;
import com.example.shared.dagger.DaggerSupport;

import java.util.Arrays;
import java.util.List;

public class ExampleApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        DaggerSupport.init(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new DataModule(),
                new DomainModule()
        );
    }
}
