package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    /**
     * Sets up the screen that follows after the user clicks the settings button
     *
     * @param v
     *            the screen view
     */
    public void goSettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }
}
