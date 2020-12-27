package com.example.dateyoureve;

import android.content.SharedPreferences;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.dateyoureve.ui.HomeFragment;
import com.polyak.iconswitch.IconSwitch;

import static android.content.Context.MODE_PRIVATE;

public class DarkMode {
    Button btnToggleDark;
    public final boolean isDarkModeOn;
    public final SharedPreferences.Editor editor;
    HomeFragment signupActivity;
    public DarkMode(final HomeFragment signupActivity) {
        this.signupActivity = signupActivity;
        SharedPreferences sharedPreferences = signupActivity.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        signupActivity.iconSwitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch (current) {

                    case LEFT:
                        if (isDarkModeOn) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor.putBoolean("isDarkModeOn", false);
                            editor.apply();
                        }
                        break;

                    case RIGHT:
                        if(!isDarkModeOn) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor.putBoolean("isDarkModeOn", true);
                            editor.apply();
                        }
                        break;
                }
            }
        });
    }
}
