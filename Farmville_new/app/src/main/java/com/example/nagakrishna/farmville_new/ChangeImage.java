package com.example.nagakrishna.farmville_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ChangeImage extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ImageView imageViewCurrentImage;
    private EditText editTextNewNumber;
    String email, image;
    private static int TAKE_PHOTO_CODE;
    String myBase64Image = null;
    static {
        TAKE_PHOTO_CODE = 1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, 0);
        email = prefs.getString("email", null);
        image = prefs.getString("image", null);

        imageViewCurrentImage = (ImageView)findViewById(R.id.ImageviewchageImage);

        if(image.equals("null")){
            imageViewCurrentImage.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
        }
        else{
            imageViewCurrentImage.setImageBitmap(decodeBase64(image));
        }

        imageViewCurrentImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeImageClick();
                    }
                }
        );

    }

    public void ChangeImageM(View v){
        try {
            if (myBase64Image == null) {
                Toast.makeText(this, "Click New Picture", Toast.LENGTH_SHORT).show();
                return;
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", email);
                    jsonObject.put("image", myBase64Image);
                    AsyncChangeImage task = new AsyncChangeImage(getBaseContext());
                    task.execute(jsonObject.toString());
                    Toast.makeText(getBaseContext(), "Changed Image Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, Account.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ChangeImageClick(){
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO_CODE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewCurrentImage.setImageBitmap(photo);
            myBase64Image = encodeToBase64(photo, Bitmap.CompressFormat.JPEG, 100);
            Log.d("CameraDemo", "Pic saved");
        }
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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
class AsyncChangeImage extends AsyncTask<String, String, String> {
    Context context;
    public AsyncChangeImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String JsonDATA = params[0];
        String urlNew = context.getResources().getString(R.string.changeImage);
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