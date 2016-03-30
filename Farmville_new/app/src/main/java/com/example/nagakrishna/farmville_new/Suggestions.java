//package com.example.nagakrishna.farmville_new;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View.OnClickListener;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Suggestions extends AppCompatActivity {
//
//    TextView textview;
//    String url;
//    public String search_word = "";
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//
//        Button getData = (Button) findViewById(R.id.button_new);
//        getData.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                EditText getWord = (EditText) findViewById(R.id.editTextWord);
//                search_word = getWord.getText().toString();
//                url = "https://api.mongolab.com/api/1/databases/insecticides/collections/crops?apiKey=s6PcYLgMZM9I-1V_bOiF531TUFcPTwi8";
//                new BackgroundActivity(context).execute(url);
//
//            }
//        });
//
//        textview = (TextView) findViewById(R.id.textViewRhymes);
//        textview.setTextColor(Color.parseColor("#E0E6E2"));
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
////        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//    static Context context;
//    View v;
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.example.prane.rhymeit/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.example.prane.rhymeit/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
//
//    public class BackgroundActivity extends AsyncTask<String, String, Void> {
//        //Params,Progress,Result
//
//        private Context context;
//        public String resultConnection = "";
//        public String result1 = "";
//        public String result2 = "";
//
//        public BackgroundActivity(Context c) {
//            context = c;
//        }
//
//
//        @Override
//        protected Void doInBackground(String... params) {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url_new = new URL(url);
//
//                // Create the request to OpenWeatherMap, and open the connection
//                urlConnection = (HttpURLConnection) url_new.openConnection();
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line = null;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                }
//                resultConnection = buffer.toString();
//            } catch (MalformedURLException e) {
//                Log.e("catch", e.toString());
//            } catch (IOException e) {
//                Log.e("catch", e.toString());
//            } finally {
//
//
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                    }
//                }
//            }
//
//            return null;
//
//        }
//
//
//        @TargetApi(Build.VERSION_CODES.KITKAT)
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            try {
//                JSONArray jsonarray = new JSONArray(resultConnection);
//                //for (int i = 0; i < jsonarray.length(); i++) {
//
//                JSONObject json = jsonarray.getJSONObject(0);
//                JSONArray json1 = json.getJSONArray("crops");
//                for (int i = 0; i < json1.length(); i++) {
//                    JSONObject json2 = json1.getJSONObject(i);
//                    result1 = json2.getString("crop");
//                    if (result1.equals(search_word)) {
//
//                        result2 = json2.getString("insecticides");
//                        //}
//                        //result1 = result1 + json.getString("word") + "\n";
//                        // JSONObject json3 = json2.getJSONObject("Crop");
//                        //result1 = json2.getString("crop");
//                    }
//                }
//                textview.setText(result2);
//
//            }
//            catch (JSONException je) {
//                je.printStackTrace();
//            }
//
//
//        }
//
//    }
//
//
//}