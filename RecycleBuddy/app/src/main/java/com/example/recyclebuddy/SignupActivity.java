package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    /**
     * Sets up the screen that comes up when the user clicks the 'cancel' button during
     * the signup phase. It brings the user back to the first page that gives them the option
     * to signup or make an account.
     *
     * @param v
     *            the screen view
     */
    public void cancel(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
