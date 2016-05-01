package com.example.nagakrishna.farmville_new;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
/**
 * Created by Naga Krishna on 29-04-2016.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    //If the token is changed registering the device again
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
