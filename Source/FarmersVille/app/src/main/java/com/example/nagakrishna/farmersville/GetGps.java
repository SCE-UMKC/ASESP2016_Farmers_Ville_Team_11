package com.example.nagakrishna.farmersville;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Naga Krishna on 12-02-2016.
 */
public class GetGps implements LocationListener {

    Context context;
    boolean IsGpsEnabled = false;
    boolean IsNetworkEnabled = false;
    boolean CanGetLocation = false;

    Location location;
    double latitude, longitude;

    private static final long MIN_DISTANCE = 10;
    private static final long MIN_TIME = 1000 * 60 * 1;

    private LocationManager locationManager;

    public GetGps(Context context) {
        this.context = context;
        GeoLocation();
    }

    public double GetLatitude(){
        if (location!=null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double GetLongitude(){
        if (location!=null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public Location GeoLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            IsGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            IsNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!IsNetworkEnabled && !IsNetworkEnabled) {

            } else {
                this.CanGetLocation = true;
                if (IsNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_DISTANCE, MIN_TIME, this);
                    if(locationManager!=null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location!=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (IsGpsEnabled) {
                    if (location == null) {


                        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DISTANCE, MIN_TIME, this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location!=null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }

                    }
                }


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}