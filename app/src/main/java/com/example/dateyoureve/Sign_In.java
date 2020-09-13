package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);
    }

    public void signup(View view) {
        Intent intant = new Intent(Sign_In.this,SignupActivity.class);
        startActivity(intant);
    }
}