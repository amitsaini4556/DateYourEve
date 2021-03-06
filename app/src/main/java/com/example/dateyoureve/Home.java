package com.example.dateyoureve;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dateyoureve.ui.FavouriteFragment;
import com.example.dateyoureve.ui.HomeFragment;
import com.example.dateyoureve.ui.ProfileFragment;
import com.example.dateyoureve.ui.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class Home extends AppCompatActivity {
    public static final int RequestPermissionCode = 7;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    boolean isDarkModeOn;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
//        if (isDarkModeOn){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        if (isOnline()) {
            if(CheckingPermissionIsEnabledOrNot())
            {
                //Toast.makeText(Home.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
            }

            // If, If permission is not enabled then else condition will execute.
            else {

                //Calling method to enable permission.
                RequestMultiplePermission();

            }
            Intent intent = getIntent();
            toolbar = findViewById(R.id.toolbar_appbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.title_home);
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(intent.getStringExtra("latitude"),intent.getStringExtra("longit"),intent.getStringExtra("country"),intent.getStringExtra("locality"),intent.getStringExtra("addr"))).commit();
            bottomMenu();
        } else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d("test", "Show Dialog: " + e.getMessage());
            }
        }
        // If All permission is enabled successfully then this block will execute.

    }

    private boolean isOnline() {
            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
    }

    //Permission function starts from here
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(Home.this, new String[]
                {
                        SEND_SMS,
                        CALL_PHONE,
                        ACCESS_FINE_LOCATION
                }, RequestPermissionCode);

    }
    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean SendSMSPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean PhoneCallPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean UserLocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;


                    if (SendSMSPermission && PhoneCallPermission && UserLocationPermission) {

                        Toast.makeText(Home.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Home.this,"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    // Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.create_event:
                Intent intent=new Intent(this,CreateEvent.class);
                startActivity(intent);
                return true;
            case R.id.past_events:
                Intent intent1 = new Intent(this,PastEventsList.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void bottomMenu() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                if (R.id.navigation_home == id) {
                    Intent intent = getIntent();
                    fragment = new HomeFragment(intent.getStringExtra("latitude"),intent.getStringExtra("longit"),intent.getStringExtra("country"),intent.getStringExtra("locality"),intent.getStringExtra("addr"));
                    toolbar.setTitle(getResources().getString(R.string.title_home));
                } else if (R.id.navigation_pro == id) {
                    fragment = new ProfileFragment();
                    toolbar.setTitle(getResources().getString(R.string.title_profile));
                } else if (R.id.navigation_rec == id) {
                    fragment = new RecentFragment();
                    toolbar.setTitle("Registered Events");
                }
//                else if (R.id.navigation_not == id) {
//                    fragment = new NotificationFragment();
//                    toolbar.setTitle(getResources().getString(R.string.title_notifications));
//                }
                else if (R.id.navigation_fav == id) {
                    fragment = new FavouriteFragment();
                    toolbar.setTitle(getResources().getString(R.string.title_favourite));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
        });
    }

}