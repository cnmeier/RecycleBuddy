package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RecyclingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);
    }

    /**
     * Sets up the screen that allows the user to go back to the previous page
     *
     * @param v
     *            the screen view
     */
    public void goBack(View v) {
        Intent intent = new Intent(this, TipsActivity.class);
        this.startActivity(intent);
    }
}
