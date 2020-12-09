package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity{
    SignupActivity signupActivity;
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    TextInputLayout textInputUser;
    TextInputLayout textInputPhone;
    Button btnToggleDark;
    Double latitude, longit;
    String country,locality,addr;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");

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
        registerUser(textInputEmail.getEditText().getText().toString(),textInputPassword.getEditText().getText().toString());

    }
    public void registerUser(String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "done",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    public  void  updateUI(FirebaseUser currentUser)
    {
        User user = new User(textInputUser.getEditText().getText().toString(),textInputPassword.getEditText().getText().toString(),textInputEmail.getEditText().getText().toString(),textInputPhone.getEditText().getText().toString());
        mDatabaseReference.child(currentUser.getUid()).setValue(user);
        Intent intant = new Intent(SignupActivity.this,Home.class);
        startActivity(intant);
        this.finish();
    }

    public void signin(View view) {
        Intent intant = new Intent(SignupActivity.this,Sign_In.class);
        startActivity(intant);
        this.finish();
    }
}