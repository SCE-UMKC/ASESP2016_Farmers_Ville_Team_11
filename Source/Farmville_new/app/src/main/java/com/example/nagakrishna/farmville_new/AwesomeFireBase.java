package com.example.nagakrishna.farmville_new;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Naga Krishna on 26-04-2016.
 */
public class AwesomeFireBase extends Application {

    private static final String TAG = "AwesomeFireBase";


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
