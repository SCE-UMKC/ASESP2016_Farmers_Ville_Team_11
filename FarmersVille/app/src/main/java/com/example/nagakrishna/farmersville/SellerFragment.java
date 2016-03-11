package com.example.nagakrishna.farmersville;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class SellerFragment extends Fragment implements OnClickListener {
    private static int TAKE_PHOTO_CODE;
    Bitmap photo;
    ImageView sellerProductImage;
    private Spinner spinnerUnits;

    static {
        TAKE_PHOTO_CODE = 1;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        this.spinnerUnits = (Spinner) view.findViewById(R.id.units);
        this.sellerProductImage = (ImageView) view.findViewById(R.id.imageValue);
        this.sellerProductImage.setImageDrawable(getResources().getDrawable(R.drawable.cameraicon));
        this.sellerProductImage.setOnClickListener(this);
        this.spinnerUnits.setAdapter(new ArrayAdapter(getActivity(), R.layout.fragment_seller, new String[]{"Kgs", "Pounds", "Gallons"}));
        return view;
    }

    public void CameraImage(View view) {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PHOTO_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE) {
            this.photo = (Bitmap) data.getExtras().get("data");
            this.sellerProductImage.setImageBitmap(this.photo);
            Log.d("CameraDemo", "Pic saved");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageValue /*2131558533*/:
                CameraImage(v);
            default:
        }
    }
}
