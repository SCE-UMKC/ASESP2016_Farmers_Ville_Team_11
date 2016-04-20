package com.example.nagakrishna.farmville_new;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

public class Registration extends AppCompatActivity {

    private EditText nameText;
    private EditText emailText;
    private EditText numberText;
    private EditText passwordText, cpasswordText;
    private Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        nameText = (EditText)findViewById(R.id.input_name);
        emailText = (EditText)findViewById(R.id.input_email);
        numberText = (EditText)findViewById(R.id.input_number);
        passwordText = (EditText)findViewById(R.id.input_password);
        cpasswordText = (EditText)findViewById(R.id.input_cpassword);
        signupButton = (Button)findViewById(R.id.btn_signup);

    }

    public void SignUpr(View v){
        if (!validate()) {
            onSignupFailed();
            return;
        }
        else{
            signupButton.setEnabled(false);
            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String number = numberText.getText().toString();
            String password = passwordText.getText().toString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                jsonObject.put("number", number);
                jsonObject.put("fullname", name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jsonObject.length()>0){
                new SendData().execute(String.valueOf(jsonObject));
                onSignupSuccess();
            }

        }

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Registration Successful", Toast.LENGTH_LONG).show();
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String cpassword = cpasswordText.getText().toString();
        String number = numberText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (number.isEmpty() || !Patterns.PHONE.matcher(number).matches()) {
            numberText.setError("enter a valid phone number");
            valid = false;
        } else {
            numberText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (!cpassword.equals(password)) {
            cpasswordText.setError("Password doesn't match");
            valid = false;
        } else {
            cpasswordText.setError(null);
        }

        return valid;
    }

}

class SendData extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... params) {
        String JsonResponse = null;
        String JsonDATA = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://farmvillenew.wymuaipgr8.us-west-2.elasticbeanstalk.com/restService/user");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream

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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
