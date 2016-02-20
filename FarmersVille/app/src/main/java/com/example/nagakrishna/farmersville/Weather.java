package com.example.nagakrishna.farmersville;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Weather extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    private Boolean CELCIUS = false;
    Context mContext;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getBaseContext();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.a01d);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
       // ForecastDay forecastDay = new ForecastDay();

        GetGps getGps = new GetGps(getApplicationContext());
        latitude = getGps.GetLatitude();
        longitude = getGps.GetLongitude();
        ButtonClick();

    }




    private void hideKeyboard(View editableView) {
        InputMethodManager imm = (InputMethodManager)mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editableView.getWindowToken(), 0);
    }

    public void ButtonClick()
    {
//        final EditText txtLocation = (EditText) findViewById(R.id.txtWeather_area);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                hideKeyboard(txtLocation);
//            }
//        });
//
//
//        final TextView txtLabelTemperature = (TextView)findViewById(R.id.labelTemperature);
//        final TextView txtTemperature = (TextView)findViewById(R.id.txtWeather_temp);
//        final TextView texLabelTemperature_min = (TextView)findViewById(R.id.labelTemperature_min);
//        final TextView txtTemperature_min = (TextView)findViewById(R.id.txtWeather_temp_min);
//        final TextView texLabelTemperature_max = (TextView)findViewById(R.id.labelTemperature_max);
//        final TextView txtTemperature_max = (TextView)findViewById(R.id.txtWeather_temp_max);
//        final TextView  txtLabelWindSpeed = (TextView)findViewById(R.id.labelWindSpeed);
//        final TextView txtWindSpeed = (TextView)findViewById(R.id.txtWindSpeed);
//        final TextView txtLabelPressure = (TextView)findViewById(R.id.labelPressure);
//        final TextView txtPressure = (TextView)findViewById(R.id.txtPressure);
//        final TextView txtLabelHumidity = (TextView)findViewById(R.id.labelHumidity);
//        final TextView txtHumididty = (TextView)findViewById(R.id.txtHumidity);
//        final TextView txtLablePlace = (TextView)findViewById(R.id.labelPlace);
//        final TextView txtPlace = (TextView)findViewById(R.id.txtPlace);
//        String Area = txtLocation.getText().toString();
//        Area = Area.replaceAll(" ", "%20");
    String Area = "naga";
        CallAPI callAPI = new CallAPI(new WeatherServiceListener() {
            @Override
            public void servicesuccess(String output) {
                try
                {
                    WeatherForecast forecast = new WeatherForecast();

                    JSONObject data = new JSONObject(output.toString());
                    Results(data);


                    JSONArray jArr = data.getJSONArray("list");
//                    for (int i=0; i < jArr.length(); i++) {
//                        JSONObject jDayForecast = jArr.getJSONObject(i);
//
//                        // Now we have the json object so we can extract the data
//                        ForecastDay df = new ForecastDay();
//
//                        // We retrieve the timestamp (dt)
//                        df.timestamp = jDayForecast.getLong("dt");
//                        String date =  df.getStringDate(jDayForecast.getLong("dt"));
//
//                        // Temp is an object
//                        JSONObject jTempObj = jDayForecast.getJSONObject("main");
//                        df.forecastTemp.temp = (float) jTempObj.getDouble("temp");
//                        df.forecastTemp.temp_min = (float) jTempObj.getDouble("temp_min");
//                        df.forecastTemp.temp_max = (float) jTempObj.getDouble("temp_max");
//                        df.forecastTemp.pressure = (float) jTempObj.getDouble("pressure");
//                        df.forecastTemp.humidity = (float) jTempObj.getDouble("humidity");
//
//
//
//
//                        // ...and now the weather
//                        JSONArray jsonArrayWeather = jDayForecast.getJSONArray("weather");
//                        JSONObject jWeatherObj = jsonArrayWeather.getJSONObject(0);
//                        df.weatherDetails.id = jWeatherObj.getInt("id");
//                        df.weatherDetails.description = jWeatherObj.getString("description");
//                        df.weatherDetails.main = jWeatherObj.getString("main");
//                        df.weatherDetails.icon = jWeatherObj.getString("icon");
//                        df.dateTime = jDayForecast.getString("dt_txt");
//                        forecast.addForecast(df);
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        callAPI.execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=44db6a862fba0b067b1930da0d769e98");
    }

    public void Results(JSONObject data)
    {
        List<ForecastDay> itemlist= new ArrayList<ForecastDay>();
        ListView listView = (ListView)findViewById(R.id.listview);
        CustomListAdapter customListAdapter;
        try{
//            JSONObject rsponse = jsonObject.optJSONObject("response");

            JSONArray jArr = data.getJSONArray("list");
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jDayForecast = jArr.getJSONObject(i);

                // Now we have the json object so we can extract the data
                ForecastDay df = new ForecastDay();
                String s = jDayForecast.getString("dt_txt");

                JSONObject jsonClouds = jDayForecast.getJSONObject("clouds");
                df.setClouds(jsonClouds.getInt("all"));

                JSONObject jsonWind = jDayForecast.getJSONObject("wind");
                df.setWindspeed(jsonWind.getDouble("speed"));


                JSONObject jTempObj = jDayForecast.getJSONObject("main");
                df.setTemperature(jTempObj.getDouble("temp"));
                df.setPressure(jTempObj.getDouble("pressure"));
                df.setHumidity(jTempObj.getDouble("humidity"));

                JSONArray jsonArrayWeather = jDayForecast.getJSONArray("weather");
                JSONObject jWeatherObj = jsonArrayWeather.getJSONObject(0);
                df.setDescription(jWeatherObj.getString("description"));
                df.setMain(jWeatherObj.getString("main"));
                String icon = jWeatherObj.getString("icon");
//                icon = "http://openweathermap.org/img/w/" + icon + ".png";
                df.setIcon(icon);

                df.setDateTime(s);
                itemlist.add(df);
                //forecast.addForecast(df);
            }


        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        customListAdapter = new CustomListAdapter(this, itemlist);
        listView.setAdapter(customListAdapter);
    }

}

class CallAPI extends AsyncTask<String, String, String>
{
    private WeatherServiceListener listener;
    public CallAPI(WeatherServiceListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        InputStream inputStream;
        StringBuilder result = null;

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            //return e.getMessage();
        }
        return  result.toString();

    }

    protected void onPostExecute(String result)
    {
        listener.servicesuccess(result);
    }
}