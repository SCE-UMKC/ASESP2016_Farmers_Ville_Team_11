package com.example.nagakrishna.farmville_new;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Naga Krishna on 27-04-2016.
 */
public class UsersChatModel{

    private String fullName;
    private String chatName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }


}
