package com.example.dateyoureve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_In extends AppCompatActivity {
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);
        mAuth = FirebaseAuth.getInstance();
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();
        }
    }

    public void confirmInput(View v) {
        InputValidation inputValidation = new InputValidation( this);
        if (!inputValidation.validateEmailSignIn() | !inputValidation.validatePasswordSignIn()) {
            return;
        }
        String email = textInputEmail.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    progressDialog.dismiss();
                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent = new Intent(getApplicationContext(),Home.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
    }

    public void signup(View view) {
        Intent intant = new Intent(Sign_In.this,SignupActivity.class);
        startActivity(intant);
        this.finish();
    }

    public void forget_password(View view)
    {
        Intent intent=new Intent(Sign_In.this,Forget_Password.class);
        startActivity(intent);
    }
}