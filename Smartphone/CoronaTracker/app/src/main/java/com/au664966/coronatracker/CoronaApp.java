package com.au664966.coronatracker;

import android.app.Application;
import android.content.Intent;

import com.au664966.coronatracker.service.foreground.RefreshDataService;

public class CoronaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
