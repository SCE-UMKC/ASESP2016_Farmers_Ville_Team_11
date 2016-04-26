package com.example.nagakrishna.farmville_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String TAG = "LoginActivity";
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private String nameUser;
    private String emailUser;
    private String imageUser, numberUser, addressUser;
//    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);


    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void SignUp(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }



    public void Login(final View view){

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        final String url = getResources().getString(R.string.loginCheck) + emailText.getText().toString()+ "&password=" + passwordText.getText().toString();
        new CallLogin(new LoginServiceListener() {
            @Override
            public void servicesuccess(String str) {
                if(str.length() < 3) {
                    progressDialog.dismiss();
                    Toast.makeText(view.getContext(), "Please check your mail and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(str);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        nameUser = jsonObject.getString("fullname");
                        emailUser = jsonObject.getString("email");
                        imageUser = jsonObject.getString("image");
                        numberUser = jsonObject.getString("number");
                        addressUser = jsonObject.getString("address");
                        Log.i(TAG, nameUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("name", nameUser);
                    editor.putString("email", emailUser);
                    editor.putString("image", imageUser);
                    editor.putString("number", numberUser);
                    editor.putString("address", addressUser);
                    editor.commit();
                    onLoginSuccess();
                    startActivity(new Intent(view.getContext(), NavigationActvity.class));
                }
            }
        }).execute(url);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Welcome " + nameUser, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}

class CallLogin extends AsyncTask<String, String, String>{

    private LoginServiceListener listener;
    public CallLogin(LoginServiceListener listener)
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
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        listener.servicesuccess(result);
    }
}
