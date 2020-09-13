package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signin(View view) {
        Intent intant = new Intent(SignupActivity.this,Sign_In.class);
        startActivity(intant);
    }
}