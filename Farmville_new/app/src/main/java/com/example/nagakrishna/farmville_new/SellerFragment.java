package com.example.nagakrishna.farmville_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.UnknownHostException;
import java.util.Set;


public class SellerFragment extends Fragment  implements OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static int TAKE_PHOTO_CODE;
    Bitmap photo;
    ImageView sellerProductImage;
    private Spinner spinnerUnits;
    private Button post;
    EditText productName;
    EditText quantity;
    EditText description;
    private String myBase64Image;

    static {
        TAKE_PHOTO_CODE = 1;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
//        spinnerUnits = (Spinner) view.findViewById(R.id.units);
        sellerProductImage = (ImageView) view.findViewById(R.id.imageValue);
        sellerProductImage.setImageDrawable(getResources().getDrawable(R.drawable.cameraicon));
        sellerProductImage.setOnClickListener(this);
        post = (Button)view.findViewById(R.id.btnSellerSubmit);
        productName = (EditText)view.findViewById(R.id.product);
        quantity = (EditText)view.findViewById(R.id.unitsValue);
        description = (EditText)view.findViewById(R.id.descriptionValue);
        post.setOnClickListener(this);

        try {
            //        spinnerUnits.setAdapter(new ArrayAdapter(getActivity(), R.layout.fragment_seller, new String[]{"Kgs", "Pounds", "Gallons"}));
//            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.items, R.layout.fragment_seller);
//            spinnerUnits.setAdapter(adapter);
//        spinnerUnits.setOnItemClickListener(this);
            return view;
        }
        catch (Exception e){
            String s = e.getMessage();
        }

        return view;

    }

    /* Converting image to Base64 String*/
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public void CameraImage(View view) {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO_CODE);
    }



    public void saveDetails(View v)  {

        SellerDetails contact = new SellerDetails();
        contact.product = productName.getText().toString();
        contact.quantity = quantity.getText().toString();
        contact.description = description.getText().toString();

        //retrieving data from shared preference
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
        String email = prefs.getString("email", null);
        contact.email = email;
        try{
            //String myBase64Image = encodeToBase64(photo, Bitmap.CompressFormat.JPEG, 100);
            contact.imageEncoderValue = myBase64Image;
        }
        catch (Exception e){
          Log.v("fin", e.getMessage().toString());
        }


        MongoAsyncTask tsk = new MongoAsyncTask();
        tsk.execute(contact);
        Toast.makeText(this.getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE) {
            this.photo = (Bitmap) data.getExtras().get("data");
            this.sellerProductImage.setImageBitmap(this.photo);
            myBase64Image = encodeToBase64(photo, Bitmap.CompressFormat.JPEG, 100);
            Log.d("CameraDemo", "Pic saved");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageValue /*2131558533*/:
                CameraImage(v);
                break;
            case R.id.btnSellerSubmit:
                saveDetails(v);
                break;
            default:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}