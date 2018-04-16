package com.oeasy.ordereasy.Modals;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stan on 4/16/2018.
 */

public class UserInformation {
    String username;
    String email;
    String userImg;
    String id;

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
}
