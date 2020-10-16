package com.example.dateyoureve;

import android.util.Patterns;

import java.util.regex.Pattern;

public class InputValidation extends SignupActivity{
    SignupActivity signupActivity;
    Sign_In sign_in;

    public InputValidation(SignupActivity signupActivity) {
        this.signupActivity = signupActivity;
    }
    public InputValidation(Sign_In sign_in) {
        this.sign_in = sign_in;
    }
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private static final Pattern USER_NAME =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private static final Pattern VALID_PHONE =
            Pattern.compile("^" +
                    "(?=\\S+$)" +           //no white spaces
                    ".{10,}" +               //at least 10 characters
                    "$");

    //Sign Up inputs validation
    boolean validateEmail() {
        String emailInput = signupActivity.textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            signupActivity.textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            signupActivity.textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            signupActivity.textInputEmail.setError(null);
            return true;
        }
    }
    boolean validateUsername() {
        String userInput = signupActivity.textInputUser.getEditText().getText().toString().trim();
        if (userInput.isEmpty()) {
            signupActivity.textInputUser.setError("Field can't be empty");
            return false;
        } else if (!USER_NAME.matcher(userInput).matches()) {
            signupActivity.textInputUser.setError("Please enter a valid username");
            return false;
        } else {
            signupActivity.textInputUser.setError(null);
            return true;
        }
    }
    boolean validatePhone() {
        String phoneInput = signupActivity.textInputPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            signupActivity.textInputPhone.setError("Field can't be empty");
            return false;
        } else if (!VALID_PHONE.matcher(phoneInput).matches()) {
            signupActivity.textInputPhone.setError("Please enter a valid mobile no.");
            return false;
        } else {
            signupActivity.textInputPhone.setError(null);
            return true;
        }
    }
    boolean validatePassword() {
        String passwordInput = signupActivity.textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            signupActivity.textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            signupActivity.textInputPassword.setError("Password too weak");
            return false;
        } else {
            signupActivity.textInputPassword.setError(null);
            return true;
        }
    }
    //Sign In inputs validation
    boolean validateEmailSignIn() {
        String emailInput = sign_in.textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            sign_in.textInputEmail.setError("Field can't be empty");
            return false;
        }else {
            sign_in.textInputEmail.setError(null);
            return true;
        }
    }
    boolean validatePasswordSignIn() {
        String passwordInput = sign_in.textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            sign_in.textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            sign_in.textInputPassword.setError(null);
            return true;
        }
    }
}
