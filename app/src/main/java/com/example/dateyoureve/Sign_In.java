package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Sign_In extends AppCompatActivity {
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);

    }

    public void confirmInput(View v) {
        InputValidation inputValidation = new InputValidation( this);
        if (!inputValidation.validateEmailSignIn() | !inputValidation.validatePasswordSignIn()) {
            return;
        }
        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    public void signup(View view) {
        Intent intant = new Intent(Sign_In.this,SignupActivity.class);
        startActivity(intant);
        this.finish();
    }
}