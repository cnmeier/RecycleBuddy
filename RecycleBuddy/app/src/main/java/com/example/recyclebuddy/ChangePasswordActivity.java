package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app for where the user changes their password and
     * displays a dialog box that states whether it worked or not
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
    }

    /**
     * Opens the app on the start
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that allows the user to change their password on their
     * profile page
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that goes to the settings page
     *
     * @param v
     *            the screen view
     */
    public void goSettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }
}
