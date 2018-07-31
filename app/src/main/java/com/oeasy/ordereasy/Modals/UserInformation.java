package com.oeasy.ordereasy.Modals;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stan on 4/16/2018.
 */

public class UserInformation implements Parcelable{
    String username;
    String email;
    String userImg;
    String id;
    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel in) {
            return new UserInformation(in);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
    public UserInformation(){}
    public UserInformation(Parcel in) {
        username = in.readString();
        email = in.readString();
        userImg = in.readString();
        id = in.readString();
    }



    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setId(String id) {
        this.id = id;
    }
    public JSONObject getJSONObject(){
        JSONObject jObject=new JSONObject();
        try {
            jObject.put("username",username);
            jObject.put("email",email);
            jObject.put("img",userImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(userImg);
        parcel.writeString(id);
    }
}
