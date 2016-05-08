package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ChangeAddress extends AppCompatActivity  {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private TextView txtCurrentAddress;
    private EditText editTextNewAddress;
    private Double latitude, longitude;
    String email, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
        email = prefs.getString("email", null);
        address = prefs.getString("address", null);

        txtCurrentAddress = (TextView)findViewById(R.id.txtCurrentAddress);
        editTextNewAddress = (EditText)findViewById(R.id.changeaddress);
        if(address.equals("null")){
            txtCurrentAddress.setText("no address");
        }
        else{
            txtCurrentAddress.setText(address);
        }
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

   public void GetCurrentAddress(View v){
       Geocoder geocoder;
       List<Address> addresses;
       geocoder = new Geocoder(this, Locale.getDefault());
       GetGps getGps = new GetGps(this);
       latitude = getGps.GetLatitude();
       longitude = getGps.GetLongitude();
       try {
           addresses = geocoder.getFromLocation(latitude, longitude, 1);
           String address = addresses.get(0).getAddressLine(0);
           String city = addresses.get(0).getLocality();
           String state = addresses.get(0).getAdminArea();
           String country = addresses.get(0).getCountryName();
           String postalCode = addresses.get(0).getPostalCode();
           String knownName = addresses.get(0).getFeatureName();
           editTextNewAddress.setText(address + " " + city + " " + state + " " + country);
       } catch (IOException e) {
           e.printStackTrace();
       }




   }

    public void ChangeAddressM(View v){

        if (!validate()) {
            Toast.makeText(this, "Try Again!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = editTextNewAddress.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, 0).edit();
        editor.putString("address", address);
        editor.commit();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("addressNew", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncChangeAddress task = new AsyncChangeAddress(getBaseContext());
        task.execute(jsonObject.toString());
        Toast.makeText(getBaseContext(), "Changed Address Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Account.class));
    }

    public boolean validate() {
        boolean valid = true;

        String address = editTextNewAddress.getText().toString();
        if (address.isEmpty() || address.length() < 5) {
            editTextNewAddress.setError("at least 5 characters");
            valid = false;
        } else {
            editTextNewAddress.setError(null);
        }


        return valid;
    }

}

class AsyncChangeAddress extends AsyncTask<String, String, String> {
    Context context;
    public AsyncChangeAddress(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String JsonDATA = params[0];
        String urlNew = context.getResources().getString(R.string.changeAddress);
        try {
            URL url = new URL(urlNew);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}