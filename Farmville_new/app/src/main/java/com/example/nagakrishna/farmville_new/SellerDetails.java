package com.example.nagakrishna.farmville_new;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
public class SellerDetails {

    public String product;
    public String doc_id;
    public String quantity;
    public String description;
    public String imageEncoderValue;



    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageEncoderValue(String imageEncoderValue) {
        this.imageEncoderValue = imageEncoderValue;
    }

    public Bitmap getImageEncoderValue() {
        Bitmap myBitmapAgain = decodeBase64(imageEncoderValue);
        return myBitmapAgain;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }



}
