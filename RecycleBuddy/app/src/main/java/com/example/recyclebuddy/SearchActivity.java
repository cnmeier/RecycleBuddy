// Chelsea is the best

package com.example.recyclebuddy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class SearchActivity extends AppCompatActivity {
    // URL to Food Data website
    URL url = null;
    // Will connect via HTTP request
    HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    final String apiKey = "TwhquiE2SVeARzinThNtPGrYntlANHxUpyI7iXGc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Connecting to Food Data website to search for products
        AsyncRetrieveFilter foodSearch = new AsyncRetrieveFilter();
        foodSearch.execute();

    }

    /**
     * Sets up the screen that follows after the user clicks the signup button
     * on the first screen
     *
     * @param v
     *            the screen view
     */
    public void goarticles(View v) {
        Intent intent = new Intent(this, ArticlesActivity.class);
        this.startActivity(intent);
    }


    protected class AsyncRetrieveFilter extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... params) {
            try {
                // Address to Food Data website, will allow us to search for foods in their database

                url = new URL("https://api.nal.usda.gov/fdc/v1/search?api_key=" + apiKey);

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data from json file
                conn.setDoInput(true);
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                Log.w("SearchActivity", valueOf(response_code));
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());
                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }

        }
    }






}