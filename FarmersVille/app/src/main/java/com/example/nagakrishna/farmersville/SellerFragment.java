package com.example.nagakrishna.farmersville;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SellerFragment extends Fragment implements View.OnClickListener {

    private Spinner spinnerUnits;
    private static int TAKE_PHOTO_CODE = 1;
    Bitmap photo;
    ImageView sellerProductImage;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        spinnerUnits = (Spinner)view.findViewById(R.id.units);
        sellerProductImage = (ImageView)view.findViewById(R.id.imageValue);
        sellerProductImage.setImageDrawable(getResources().getDrawable(R.drawable.cameraicon));
        sellerProductImage.setOnClickListener(this);
        String values [] = {"Kgs", "Pounds", "Gallons"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, values );
        spinnerUnits.setAdapter(arrayAdapter);

//        spinnerUnits.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    }
//                }
//        );

        return view;

    }


    public void CameraImage(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE) {
            photo = (Bitmap) data.getExtras().get("data");
            //Intent intent = new Intent(this, MapsActivity.class);
            //intent.putExtra("image_camera", photo);
            sellerProductImage.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageValue:
                CameraImage(v);
        }
    }
}
