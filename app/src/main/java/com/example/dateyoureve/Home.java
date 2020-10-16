package com.example.dateyoureve;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.dateyoureve.ui.FavouriteFragment;
import com.example.dateyoureve.ui.HomeFragment;
import com.example.dateyoureve.ui.NotificationFragment;
import com.example.dateyoureve.ui.ProfileFragment;
import com.example.dateyoureve.ui.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar_appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(intent.getStringExtra("latitude"),intent.getStringExtra("longit"),intent.getStringExtra("country"),intent.getStringExtra("locality"),intent.getStringExtra("addr"))).commit();
        bottomMenu();
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
                    toolbar.setTitle(getResources().getString(R.string.title_recent));
                } else if (R.id.navigation_not == id) {
                    fragment = new NotificationFragment();
                    toolbar.setTitle(getResources().getString(R.string.title_notifications));
                } else if (R.id.navigation_fav == id) {
                    fragment = new FavouriteFragment();
                    toolbar.setTitle(getResources().getString(R.string.title_favourite));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
        });
    }

}