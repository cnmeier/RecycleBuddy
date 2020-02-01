package com.example.recyclebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Sets up the screen that follows after the user clicks the signup button
     * on the first screen
     *
     * @param v
     *            the screen view
     */
    public void gosignup(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the login button
     * on the first screen and their username/password combination were correct
     *
     * @param v
     *            the screen view
     */
    public void gosearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        this.startActivity(intent);
    }
}
