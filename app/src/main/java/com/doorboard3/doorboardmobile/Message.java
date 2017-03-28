package com.doorboard3.doorboardmobile;

import android.graphics.Bitmap;

/**
 * Created by danielchen on 3/28/17.
 */

public class Message {
    Bitmap profilePic;
    String name;
    String status;
    String dateTime;

    public Message(Bitmap profilePic, String name, String status, String dateTime) {
        this.profilePic = profilePic;
        this.name = name;
        this.status = status;
        this.dateTime = dateTime;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
