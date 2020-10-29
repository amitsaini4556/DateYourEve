package com.example.dateyoureve;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;

import static android.content.Context.MODE_PRIVATE;

public class DarkMode{
    Button btnToggleDark;
    SignupActivity signupActivity;
    public DarkMode(final SignupActivity signupActivity){
        this.signupActivity = signupActivity;
        SharedPreferences sharedPreferences = signupActivity.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            signupActivity.btnToggleDark.setText("Disable Dark Mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            signupActivity.btnToggleDark.setText("Enable Dark Mode");
        }

        signupActivity.btnToggleDark.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (isDarkModeOn) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor.putBoolean("isDarkModeOn", false);
                            editor.apply();
                            signupActivity.btnToggleDark.setText("Enable Dark Mode");
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor.putBoolean("isDarkModeOn", true);
                            editor.apply();
                            signupActivity.btnToggleDark.setText("Disable Dark Mode");
                        }
                    }
                });
    }
}
