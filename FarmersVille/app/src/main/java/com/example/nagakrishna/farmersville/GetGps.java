package com.example.nagakrishna.farmersville;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

public class GetGps implements LocationListener {
    private static final long MIN_DISTANCE = 10;
    private static final long MIN_TIME = 60000;
    boolean CanGetLocation;
    boolean IsGpsEnabled;
    boolean IsNetworkEnabled;
    Context context;
    double latitude;
    Location location;
    private LocationManager locationManager;
    double longitude;

    public GetGps(Context context) {
        this.IsGpsEnabled = false;
        this.IsNetworkEnabled = false;
        this.CanGetLocation = false;
        this.context = context;
        GeoLocation();
    }

    public double GetLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }
        return this.latitude;
    }

    public double GetLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }
        return this.longitude;
    }

    public Location GeoLocation() {
        try {
            this.locationManager = (LocationManager) this.context.getSystemService("location");
            this.IsGpsEnabled = this.locationManager.isProviderEnabled("gps");
            this.IsNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (this.IsNetworkEnabled || this.IsNetworkEnabled) {
                this.CanGetLocation = true;
                if (this.IsNetworkEnabled) {
                    this.locationManager.requestLocationUpdates("network", MIN_DISTANCE, 60000.0f, this);
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("network");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
                if (this.IsGpsEnabled && this.location == null) {
                    if (ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                        this.locationManager.requestLocationUpdates("gps", MIN_DISTANCE, 60000.0f, this);
                    } else {
                        this.locationManager.requestLocationUpdates("gps", MIN_DISTANCE, 60000.0f, this);
                    }
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("gps");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.location;
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
