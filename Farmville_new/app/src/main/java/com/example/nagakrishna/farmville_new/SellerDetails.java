package com.example.nagakrishna.farmville_new;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.widget.ImageView;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
public class SellerDetails implements Parcelable {

    public SellerDetails(){

    }
    protected SellerDetails(Parcel in) {
        product = in.readString();
        doc_id = in.readString();
        quantity = in.readString();
        description = in.readString();
        imageEncoderValue = in.readString();
        email = in.readString();
        created = in.readString();
    }

    public static final Creator<SellerDetails> CREATOR = new Creator<SellerDetails>() {
        @Override
        public SellerDetails createFromParcel(Parcel in) {
            return new SellerDetails(in);
        }

        @Override
        public SellerDetails[] newArray(int size) {
            return new SellerDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product);
        dest.writeString(doc_id);
        dest.writeString(quantity);
        dest.writeString(description);
        dest.writeString(imageEncoderValue);
        dest.writeString(email);
        dest.writeString(created);
    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public String product;
    public String doc_id;
    public String quantity;
    public String description;
    public String imageEncoderValue;
    public String email;

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public String created;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
