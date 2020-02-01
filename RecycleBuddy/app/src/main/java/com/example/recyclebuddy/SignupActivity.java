package com.example.recyclebuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app that allows the user to signup and make an account
     * for the first time by inputting their information
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}
