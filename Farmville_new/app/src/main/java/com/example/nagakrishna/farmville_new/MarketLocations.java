package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketLocations extends AppCompatActivity {

    double latitude;
    double longitude;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetGps getGps = new GetGps(getApplicationContext());
        this.latitude = getGps.GetLatitude();
        this.longitude = getGps.GetLongitude();
        OnButtnClick();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void OnButtnClick(){

        String url = "https://api.foursquare.com/v2/venues/search?ll=" + latitude + "," + longitude + "&categoryId=4bf58dd8d48988d1fa941735&client_id=EKGH2L4JAIZMXZGGRTN3KV5PH0ZQYL3VO4N3OOSU3HKYZWQF&client_secret=RAET0NWZREI3Y5NUPQUSRMECY514AYHEOUU03BFX14SFMVXT&v=20160202";



        FourSquAPI fourSquAPI  = new FourSquAPI(
                new VenueSearchListner() {
                    @Override
                    public void service(String result) {
                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            Results(jsonObject);
                            JSONObject rsponse = jsonObject.optJSONObject("response");

                            JSONArray venues = rsponse.optJSONArray("venues");
                            for(int i=0; i<venues.length(); i++){
                                JSONObject obj = venues.getJSONObject(i);
                                String name = obj.getString("name");
                                String id1 = obj.getString("id");
                                MarketDataType market1 = new MarketDataType();
                                market1.setName(name);
                                market1.setMarketID(id1);
                                // itemlist.add(market1);
                                //  listView.setAdapter(customListAdapter);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );


        fourSquAPI.execute(url);
    }


    public void Results(JSONObject jsonObject)
    {
        List<MarketDataType> itemlist= new ArrayList<MarketDataType>();
        ListView listView = (ListView)findViewById(R.id.listview1);
//        VenueCustomListAdapter venueCustomListAdapter;
        try{
            JSONObject rsponse = jsonObject.optJSONObject("response");

            JSONArray venues = rsponse.optJSONArray("venues");
            for(int i=0; i<venues.length(); i++){
                JSONObject obj = venues.getJSONObject(i);
                String name = obj.getString("name");
                JSONObject loc = obj.getJSONObject("location");
                String id1 = loc.getString("formattedAddress");
//                id1 = id1.replace("[","");
//                id1 = id1.replace("]","");
                id1 = id1.replaceAll("[^a-zA-Z0-9,\\s]", "");
                double distanceMetres = (loc.getDouble("distance")/ 1609);
                distanceMetres = Math.round(distanceMetres * 100);
                distanceMetres = distanceMetres / 100;
//                DecimalFormat df = new DecimalFormat("####0.00");
                String distanceMiles = String.valueOf(distanceMetres);
//                distanceMiles = df.format(distanceMiles);
                distanceMiles = distanceMiles + " miles";
                MarketDataType market1 = new MarketDataType();
                market1.setName(name);
                market1.setMarketID(id1);
                market1.setDistance(distanceMiles);
                itemlist.add(market1);

            }}

        catch (JSONException e) {
            e.printStackTrace();
        }

//        venueCustomListAdapter = new VenueCustomListAdapter(this, itemlist);
//        listView.setSelector(R.drawable.list_selector_focused);
        listView.setAdapter(new VenueCustomListAdapter(this, itemlist));
//        listView.setSelector(R.drawable.list_selector_focused);
    }

}

class FourSquAPI extends AsyncTask<String, String, String> {

    private VenueSearchListner listner;
    public FourSquAPI(VenueSearchListner listner){
        this.listner = listner;
    }


    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        InputStream in;
        StringBuilder result = null;

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  result.toString();

    }

    protected void onPostExecute(String result)
    {
        listner.service(result);
    }
}
