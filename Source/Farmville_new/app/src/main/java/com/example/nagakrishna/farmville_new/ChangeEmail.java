package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class ChangeEmail extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private TextView txtCurrentEmail;
    private EditText editTextNewEmail;
    String currentEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
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
        currentEmail = prefs.getString("email", null);

        txtCurrentEmail = (TextView)findViewById(R.id.txtCurrentEmail);
        editTextNewEmail = (EditText)findViewById(R.id.edittxtchangeEmail);
        txtCurrentEmail.setText(currentEmail);

    }

    public void failed() {
        Toast.makeText(getBaseContext(), "Try again", Toast.LENGTH_LONG).show();
    }
    public void ChangeEmailM(View view){

        if (!validate()) {
            failed();
            return;
        }
        String newEmail = editTextNewEmail.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, 0).edit();
        editor.putString("email", newEmail);
        editor.commit();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", currentEmail);
            jsonObject.put("emailNew", newEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncChangeEmail task = new AsyncChangeEmail(getBaseContext());
        task.execute(jsonObject.toString());
        Toast.makeText(getBaseContext(), "Changed Email Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Account.class));

    }

    public boolean validate() {
        boolean valid = true;
        String newEmail = editTextNewEmail.getText().toString();
        if (newEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            editTextNewEmail.setError("enter a valid email address");
            valid = false;
        }
        else if(newEmail.equals(currentEmail)){
            editTextNewEmail.setError("enter new email address");
            valid = false;
        }
        else {
            editTextNewEmail.setError(null);
        }
        return valid;
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

class AsyncChangeEmail extends AsyncTask<String, String, String> {
    Context context;

    public AsyncChangeEmail(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String JsonDATA = params[0];
        String urlNew = context.getResources().getString(R.string.changeEmail);
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