package com.uniba.pronuntia;

import android.app.Application;
import android.util.Log;

public class PronuntiaApplication extends Application {

    private static final String TAG = "PronuntiaApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Applicazione creata");
    }
}
