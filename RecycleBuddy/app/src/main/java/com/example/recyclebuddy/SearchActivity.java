package com.example.recyclebuddy;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

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

                query = query.replace(" ", "+");

                // Connecting to Food Data website to search for products
                AsyncRetrieveFilter foodSearch = new AsyncRetrieveFilter(query);
                try {
                    String descripAndBrand = foodSearch.execute().get();
                    String[] itemsSplit = descripAndBrand.split("&");
                    for(int i = 0; i < itemsSplit.length; i++){
                        // Make the things
                        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
                        addItemEntitys(tableLayout, itemsSplit[i]);
                    }

                }catch(ExecutionException e){
                    Log.w("SearchActivity", e);
                }catch(InterruptedException e){
                    Log.w("SearchActivity", e);
                }

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
        Intent intent = new Intent(this, TipsActivity.class);
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

    public void addItemEntitys(TableLayout layout, String descripAndBrand){

        Log.w("SearchActivity", descripAndBrand);
        TableRow tableRow = new TableRow(getApplicationContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 200));

        tableRow.setBackgroundColor(Color.parseColor("#DDDDDD"));

        layout.addView(tableRow);
        // Add text view to the new row
        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(18f);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.parseColor("#DDDDDD"));

        //String next_bid = "Next bid: \n$" + item.split(";")[2].replace("_", "");
        textView.setText(descripAndBrand);
        textView.setHeight(200);
        textView.setWidth(220);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);


        tableRow.addView(textView);

    }

    /**
     * This class allows us to query databases from the FDA to grab nutrient facts for user queries
     */
    protected class AsyncRetrieveFilter extends AsyncTask<String, String, String> {

        String query;

        AsyncRetrieveFilter(String query){
            this.query = query;
        }

        //addItemEntitys(tableLayout, allItems);


        @Override
        public String doInBackground(String... params) {
            String description = "";
            String brandOwner = "";
            try {
                // Address to Food Data website, will allow us to search for foods in their database
                url = new URL("https://api.nal.usda.gov/fdc/v1/search?api_key=" + apiKey + "&generalSearchInput=" + this.query + "&requireAllWords=True");

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

                    String items  = "";

                    // Start at 2
                    for(int i = 2; i < map.values().toString().split("\\{").length; i++) {
                        String item = map.values().toString().split("\\{")[i];
                        //items += "$" + item;
                        //Log.w("SearchActivity", map.values().toString().split("\\{")[i]);
                        try {
                            if(item.contains("additionalDescription")) {
                                String resultString = StringUtils.substringBetween(item, "description=", ", additionalDescriptions");
                                Log.w("SearchActivity", resultString);
                                description = resultString;
                                items += description;
                            } else if(item.contains("dataType")){
                                String resultString = StringUtils.substringBetween(item, "description=", ", dataType");
                                Log.w("SearchActivity", resultString);
                                description = resultString;
                                items += description;
                            }else{
                                continue;
                            }if(item.contains("brandOwner")){
                                String resultString = StringUtils.substringBetween(item, "brandOwner=", ", ingredients");
                                Log.w("SearchActivity", resultString);
                                brandOwner = resultString;
                                items += brandOwner;

                            }
                            items += "&";
                        }

                        catch(Exception e){
                            Log.w("Search", e);
                        }



                    }
                    return (items);
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