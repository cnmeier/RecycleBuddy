package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    /**
     * Sets up the screen that follows after the user clicks the store button
     * on the search screen
     *
     * @param v
     *            the screen view
     */
    public void goSearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the articles button
     * on the search screen
     *
     * @param v
     *            the screen view
     */
    public void goNews(View v) {
        Intent intent = new Intent(this, ArticlesActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the store button
     * on the search screen
     *
     * @param v
     *            the screen view
     */
    public void goStore(View v) {
        Intent intent = new Intent(this, StoreActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the profile button
     * on the search screen
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }
}
