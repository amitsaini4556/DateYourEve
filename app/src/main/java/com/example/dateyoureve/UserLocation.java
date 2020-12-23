package com.example.dateyoureve;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class UserLocation extends EventDetails implements LocationListener {
    EventDetails eventDetails;
    int PERMISSION_ID = 44;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    UserLocation(EventDetails eventDetails){
        this.eventDetails = eventDetails;
        getLastLocation();
    }
    @SuppressLint("MissingPermission")
    public void getLastLocation()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                eventDetails.mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                        new OnCompleteListener<Location>() {
//
//                            @Override
//                            public void onComplete(
//                                    @NonNull Task<Location> task)
//                            {
//                                Location location = task.getResult();
//                                if (location == null) {
//                                    requestNewLocationData();
//                                }
//                                else {
//                                    try {
//                                        Geocoder geocoder = new Geocoder(eventDetails, Locale.getDefault());
//                                        List<Address> address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                                        eventDetails.latitude = address.get(0).getLatitude();
//                                        eventDetails.longit = address.get(0).getLongitude();
//                                        eventDetails.country = address.get(0).getCountryName();
//                                        eventDetails.locality = address.get(0).getLocality();
//                                        eventDetails.addr = address.get(0).getAddressLine(0);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//            }
//
//            else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                eventDetails.startActivity(intent);
//            }
//        }
//        else {
//            requestPermissions();
//        }
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
            eventDetails.latitude = mLastLocation.getLatitude();
            eventDetails.longit = mLastLocation.getLongitude();
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        eventDetails.addr = String.valueOf(location.getLatitude());
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
