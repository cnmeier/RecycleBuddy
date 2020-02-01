package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app that displays the users settings
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Opens the app on the start
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that displays the users profile page
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that allows the user to change their password which is
     * under their settings
     *
     * @param v
     *            the screen view
     */
    public void goChange(View v) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the login screen that the user goes to after choosing to logout
     *
     * @param v
     *            the screen view
     */
    public void goLogin(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
