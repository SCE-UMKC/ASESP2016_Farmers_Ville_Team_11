package com.example.nagakrishna.farmville_new;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View.OnClickListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Suggestions extends AppCompatActivity {

    Spinner crops;
    String result2="";
    TextView textview;
    TextView textview1;
    TextView textview2;
    TextView text;
    String url;
    public String search_word = "";
    private Double lat, lon,temp,idel;
    private LinearLayout ly1, ly2, ly3,ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        TextView getData = (TextView) findViewById(R.id.button_new);
        getData.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                crops=(Spinner)findViewById(R.id.spinner);
                search_word = crops.getSelectedItem().toString();
                if(!search_word.equals("Select Crop")) {
                    url = "https://api.mongolab.com/api/1/databases/insecticides/collections/crops?apiKey=s6PcYLgMZM9I-1V_bOiF531TUFcPTwi8";
                    new BackgroundActivity(context).execute(url);
                }
                else{
                    Toast.makeText(v.getContext(), "Select Crop", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textview = (TextView) findViewById(R.id.textViewRhymes);
        textview1 = (TextView) findViewById(R.id.textViewtemp);
        textview2 = (TextView) findViewById(R.id.textViewmonth);
        text=(TextView)findViewById(R.id.textView4);

        ly = (LinearLayout)findViewById(R.id.lySuggestions);
//        ly1 = (LinearLayout)findViewById(R.id.lySuggestions1);
//        ly2 = (LinearLayout)findViewById(R.id.lySuggestions2);
//        ly3 = (LinearLayout)findViewById(R.id.lySuggestions3);
        GetGps getGps = new GetGps(getBaseContext());
        lat = getGps.latitude;
        lon = getGps.longitude;

        new CallAPI(new WeatherServiceListener() {
            @Override
            public void servicesuccess(String str) {
                try {
                    JSONObject data = new JSONObject(str.toString());
//                    Temp(data);
                    JSONObject main = data.getJSONObject("main");
                    String temp1 = main.getString("temp");
                    temp = Double.parseDouble(temp1);
                    //  String temp5 = (String) main.get("temp");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute(new String[]{"http://api.openweathermap.org/data/2.5/weather?lat="+ lat+"&lon="+lon+"&appid=d0c4f73baa4bbd692bf152282ea0d999"});

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

//    public void Temp(JSONObject jsonObject){
//        try {
//            JSONObject k = jsonObject.getJSONObject("coord");
//            String i = k.getString("lon");
//            JSONObject j = jsonObject.getJSONObject("main");
//            String dd = j.getString("pressure");
//            String r = j.getString("temp");
//            String i1 = k.getString("lat");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
    static Context context;
    View v;

    public class BackgroundActivity extends AsyncTask<String, String, Void> {
        //Params,Progress,Result

        private Context context;
        public String resultConnection = "";
        public String result1 = "";
        // public String result2 = "";
        public String result3 = "";
        public String result4 = "";

        public BackgroundActivity(Context c) {
            context = c;
        }


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url_new = new URL(url);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url_new.openConnection();
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                resultConnection = buffer.toString();
            } catch (MalformedURLException e) {
                Log.e("catch", e.toString());
            } catch (IOException e) {
                Log.e("catch", e.toString());
            } finally {


                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonarray = new JSONArray(resultConnection);
                JSONObject json = jsonarray.getJSONObject(0);
                JSONArray json1 = json.getJSONArray("crops");
                for (int i = 0; i < json1.length(); i++) {
                    JSONObject json2 = json1.getJSONObject(i);
                    result1 = json2.getString("crop");
                    if (result1.equals(search_word)) {

                        result2 = json2.getString("insecticides");
                        result3 = json2.getString("temperature");
                        result4 = json2.getString("month");
                        idel = Double.parseDouble(result3);


                        //}
                        //result1 = result1 + json.getString("word") + "\n";
                        // JSONObject json3 = json2.getJSONObject("Crop");
                        //result1 = json2.getString("crop");
                    }
                }
//                ly1.setVisibility(View.VISIBLE);
//                ly2.setVisibility(View.VISIBLE);
//                ly3.setVisibility(View.VISIBLE);
                ly.setVisibility(View.VISIBLE);
                textview.setText(result2);

                String newTemp = new DecimalFormat("#.#").format(idel - 273.0d) + " \u00b0C";
                textview1.setText(newTemp);
                textview2.setText(result4);
                if (temp > (idel + 20) || temp < idel - 20) {
                    text.setText("bad condition to cultivate");

                } else {
                    text.setText("good condition to cultivate");
                }

            } catch (JSONException je) {
                je.printStackTrace();
            }


        }



    }
    public void text_msg(View view) {
        Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fmccrop.com/grower/Products/Insecticides-Miticides/" + result2 + ".aspx"));
        startActivity(browserintent);
    }

    public void text_insecticide(View view){
        Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ipm.ucdavis.edu/PMG/selectnewpest." + search_word + ".html"));
        startActivity(browserintent);
    }
}

