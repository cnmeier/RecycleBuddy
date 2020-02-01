// Chelsea is the best

package com.example.recyclebuddy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

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
        final SearchView searchtext;
        final Button searchbutton;
        setContentView(R.layout.activity_search);

        searchtext  = (SearchView) findViewById(R.id.simpleSearchView);
        //searchbutton = (Button) findViewById(R.id.searchButton);

        searchtext.setOnQueryTextListener(new SearchView.OnQueryTextListener()  {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.w("SearchActivity", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() >= 1){
                    Log.w("SearchActivity", newText);
                }
                return false;
            }
        });



        // Connecting to Food Data website to search for products
        AsyncRetrieveFilter foodSearch = new AsyncRetrieveFilter();
        foodSearch.execute();

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

    /**
     * This class allows us to query databases from the FDA to grab nutrient facts for user queries
     */
    protected class AsyncRetrieveFilter extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... params) {
            try {
                // Address to Food Data website, will allow us to search for foods in their database
                url = new URL("https://api.nal.usda.gov/fdc/v1/search?api_key=" + apiKey + "&generalSearchInput=chicken+taquitos&requireAllWords=True");

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
                //conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("Accept", "application/json");
                Log.w("SearchActivity", conn.getRequestMethod());
                Log.w("SearchActivity", conn.getHeaderField(0));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                Log.w("SearchActivity", valueOf(response_code));
                // Checking URL passed through
                Log.w("SearchActivity", url.toString());
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
                    Log.w("SearchActivity", result.toString());
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String,Object> map = mapper.readValue(result.toString(), Map.class);
                    Log.w("SearchActivity",map.values().toString());
                    // Start at 2
                    for(int i = 2; i < map.values().toString().split("\\{").length; i++) {
                        Log.w("SearchActivity", map.values().toString().split("\\{")[i]);
                    }
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