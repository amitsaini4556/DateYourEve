package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity{
    SignupActivity signupActivity;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    TextInputLayout textInputUser;
    TextInputLayout textInputPhone;
    Button btnToggleDark;
    Double latitude, longit;
    String country,locality,addr;

    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnToggleDark  = findViewById(R.id.darkmode);
        DarkMode darkMode = new DarkMode(this);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputUser = findViewById(R.id.text_input_username);
        textInputPhone = findViewById(R.id.text_input_phone);
    }

    public void confirmInput(View v)  {
        final InputValidation inputValidation = new InputValidation(this);
        if (!inputValidation.validateEmail() | !inputValidation.validatePassword() | !inputValidation.validateUsername() | !inputValidation.validatePhone()) {
            return;
        }
        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        Intent intant = new Intent(SignupActivity.this,Home.class);
        intant.putExtra("latitude", latitude);
        intant.putExtra("longit", longit);
        intant.putExtra("country", country);
        intant.putExtra("locality", locality);
        intant.putExtra("addr", addr);
        startActivity(intant);
        this.finish();
    }

    public void signin(View view) {
        Intent intant = new Intent(SignupActivity.this,Sign_In.class);
        startActivity(intant);
        this.finish();
    }
}