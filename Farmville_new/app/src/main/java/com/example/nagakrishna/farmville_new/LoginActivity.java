package com.example.nagakrishna.farmville_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;

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


public class LoginActivity extends AppCompatActivity
//        implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String TAG = "LoginActivity";
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private String nameUser;
    private String emailUser;
    private String imageUser, numberUser, addressUser;

//    private GoogleApiClient mGoogleApiClient;
//
//    private boolean mIntentInProgress;
//    private boolean signedInUser;
//    private ConnectionResult mConnectionResult;
//    private SignInButton signinButton;
//    private ImageView image;
//    private TextView username, emailLabel;
//
//    private static final int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);

//        signinButton = (SignInButton) findViewById(R.id.signin);
//        signinButton.setOnClickListener(this);
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();



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


//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        mGoogleApiClient.connect();
////        updateProfile(false);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.signin:
//                googlePlusLogin();
//                break;
//        }
//    }
//
//    public void signIn(View v) {
//        googlePlusLogin();
//    }
//
//    public void logout(View v) {
//        googlePlusLogout();
//    }
//
//    private void googlePlusLogin() {
//        if (!mGoogleApiClient.isConnecting()) {
//            signedInUser = true;
//            resolveSignInError();
//        }
//    }
//
//    private void googlePlusLogout() {
//        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            mGoogleApiClient.disconnect();
//            mGoogleApiClient.connect();
////            updateProfile(false);
//        }
//    }
//
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        if (!result.hasResolution()) {
//            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
//            return;
//        }
//
//        if (!mIntentInProgress) {
//            // store mConnectionResult
//            mConnectionResult = result;
//
//            if (signedInUser) {
//                resolveSignInError();
//            }
//        }
//    }
//
//    private void getProfileInformation() {
//        try {
//            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//                String personName = currentPerson.getDisplayName();
//                String personPhotoUrl = currentPerson.getImage().getUrl();
//                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
//
////                username.setText(personName);
////                emailLabel.setText(email);
//
////                new LoadProfileImage(image).execute(personPhotoUrl);
//
//                // update profile frame with new info about Google Account
//                // profile
////                updateProfile(true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onConnected(Bundle arg0) {
//        signedInUser = false;
//        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
//        getProfileInformation();
//        startActivity(new Intent(this, NavigationActvity.class));
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//        switch (requestCode) {
//            case RC_SIGN_IN:
//                if (responseCode == RESULT_OK) {
//                    signedInUser = false;
//
//                }
//                mIntentInProgress = false;
//                if (!mGoogleApiClient.isConnecting()) {
//                    mGoogleApiClient.connect();
//                }
//                break;
//        }
//    }
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    private void resolveSignInError() {
//        if (mConnectionResult.hasResolution()) {
//            try {
//                mIntentInProgress = true;
//                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
//            } catch (IntentSender.SendIntentException e) {
//                mIntentInProgress = false;
//                mGoogleApiClient.connect();
//            }
//        }
//    }
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
