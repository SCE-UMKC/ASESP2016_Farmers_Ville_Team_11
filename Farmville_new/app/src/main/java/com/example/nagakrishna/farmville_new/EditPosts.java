package com.example.nagakrishna.farmville_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class EditPosts extends AppCompatActivity {

    private String created, product, quantity, description, email, image;
    private EditText productTextview, quantityTextview, descriptionTextview;
    private ImageView imageView;
    private static int TAKE_PHOTO_CODE;
    Bitmap photo;
    private String myBase64Image = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        Bundle bundle = getIntent().getExtras();
        created = bundle.getString("created");
        product = bundle.getString("product");
        quantity = bundle.getString("quantity");
        description = bundle.getString("description");
        image = bundle.getString("image");
        email = bundle.getString("email");

        productTextview = (EditText)findViewById(R.id.listProductEditPost);
        quantityTextview = (EditText)findViewById(R.id.listQuantityEditPost);
        descriptionTextview = (EditText)findViewById(R.id.listDescriptionEditPost);
        imageView = (ImageView)findViewById(R.id.listImageViewEditPost);

        productTextview.setText(product);
        quantityTextview.setText(quantity);
        descriptionTextview.setText(description);
        if(image.equals("null")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
        }
        else {
            Bitmap myBitmapAgain = decodeBase64(image);
            imageView.setImageBitmap(myBitmapAgain);
        }
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void CameraImageEditPost(View view) {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView = (ImageView)findViewById(R.id.listImageViewEditPost);
            imageView.setImageBitmap(photo);
            myBase64Image = encodeToBase64(photo, Bitmap.CompressFormat.JPEG, 100);
            Log.d("CameraDemo", "Pic saved");
        }
//        else{
//            finish();
//        }
    }

    public void UpdateData(View v){
        final ProgressDialog progressDialog = new ProgressDialog(EditPosts.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String productEdit = productTextview.getText().toString();
        String quantityEdit = quantityTextview.getText().toString();
        String descEdit = descriptionTextview.getText().toString();
        String imageEdit;
        if(myBase64Image!=null){
            imageEdit = myBase64Image;
        }
        else {
            imageEdit = image;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("created", created);
            jsonObject.put("Product", productEdit);
            jsonObject.put("Quantity", quantityEdit);
            jsonObject.put("Description", descEdit);
            jsonObject.put("Image", imageEdit);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SendUpdatePosts(new LoginServiceListener() {
            @Override
            public void servicesuccess(String str) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Updated Post Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), temp.class));
            }
        },getBaseContext()).execute(String.valueOf(jsonObject));
//        SendUpdatePosts sendUpdatePosts = new SendUpdatePosts(getBaseContext());
//        sendUpdatePosts.execute(String.valueOf(jsonObject));

    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

//    @Override
//    public void onBackPressed() {
//        Intent setIntent = new Intent(this, temp.class);
//        startActivity(setIntent);
//    }
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

class SendUpdatePosts extends AsyncTask<String, String, String>{

    Context context;
    LoginServiceListener listener;
    public SendUpdatePosts(LoginServiceListener listener, Context context){
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.servicesuccess(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = context.getResources().getString(R.string.editPost);
        String JsonDATA = params[0];
//        InputStream inputStream;
        StringBuilder result = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
