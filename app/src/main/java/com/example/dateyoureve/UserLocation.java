package com.example.dateyoureve;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLocation extends EventDetails {
    EventDetails eventDetails;
    int PERMISSION_ID = 44;
    UserLocation(EventDetails eventDetails){
        this.eventDetails = eventDetails;
        getLastLocation();
    }
    @SuppressLint("MissingPermission")
    public void getLastLocation()
    {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                eventDetails.mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {

                            @Override
                            public void onComplete(
                                    @NonNull Task<Location> task)
                            {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                }
                                else {
                                    try {
                                        Geocoder geocoder = new Geocoder(eventDetails, Locale.getDefault());
                                        List<Address> address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                        eventDetails.latitude = address.get(0).getLatitude();
                                        eventDetails.longit = address.get(0).getLongitude();
                                        eventDetails.country = address.get(0).getCountryName();
                                        eventDetails.locality = address.get(0).getLocality();
                                        eventDetails.addr = address.get(0).getAddressLine(0);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }

            else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                eventDetails.startActivity(intent);
            }
        }
        else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData()
    {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        eventDetails.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(eventDetails);
        eventDetails.mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(
                LocationResult locationResult)
        {
            Location mLastLocation = locationResult.getLastLocation();
            Geocoder geocoder = new Geocoder(eventDetails, Locale.getDefault());
            List<Address> address = null;
            try {
                address = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            eventDetails.latitude = address.get(0).getLatitude();
            eventDetails.longit = address.get(0).getLongitude();
            eventDetails.country = address.get(0).getCountryName();
            eventDetails.locality = address.get(0).getLocality();
            eventDetails.addr = address.get(0).getAddressLine(0);
        }
    };

    private boolean checkPermissions()
    {
        return ActivityCompat.checkSelfPermission(eventDetails, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(eventDetails, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(eventDetails, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    private boolean isLocationEnabled()
    {
        LocationManager locationManager = (LocationManager) eventDetails.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
