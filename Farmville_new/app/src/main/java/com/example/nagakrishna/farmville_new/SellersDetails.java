package com.example.nagakrishna.farmville_new;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

//http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/emailcheck/EmailCheck?email=nagakrishna@gmail.com

public class SellersDetails extends AppCompatActivity {

    private TextView nameText, numberText, addressText;
    private String returnValues;
    private String fullname, address, number, reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nameText = (TextView) findViewById(R.id.txtFullnameValue);
        numberText = (TextView) findViewById(R.id.txtNumberValue);
        addressText = (TextView) findViewById(R.id.txtAddressValue);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("SellerEmail");


        GetSellerDetails task = new GetSellerDetails();
        try {
            returnValues = task.execute(email).get();
            JSONArray jsonArray = new JSONArray(returnValues);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            fullname = jsonObject.getString("fullname");
            address = jsonObject.getString("address");
            number = jsonObject.getString("number");
            nameText.setText(fullname);
            numberText.setText(number);
            addressText.setText(address);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void CallNumberIntent(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+" + numberText.getText().toString().trim()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public void StartMapsIntent(View v){
        Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                +addressText.getText().toString()));
        startActivity(geoIntent);
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



}

class GetSellerDetails extends AsyncTask<String, String, String>{

    static String server_output = null;
    static String temp_output = null;
    StringBuilder result = null;
    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String urlNew = "http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/emailcheck/EmailCheck?email=" + email;
        try {
            URL url = new URL(urlNew);

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            result = new StringBuilder();
            while ((temp_output = br.readLine()) != null) {
                result.append(temp_output);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

//class SellerDetailsActivity{
//    String adderss;
//    String name;
//    String number;
//    String reviews;
//}
