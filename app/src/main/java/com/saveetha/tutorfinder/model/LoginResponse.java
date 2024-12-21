package com.saveetha.tutorfinder.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("user_logged_in")
    private String user_logged_in;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("user_profile_image")
    private String user_profile_image;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getUser_logged_in() {
        return Boolean.valueOf(user_logged_in);
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getUser_profile_image() {
        return user_profile_image;
    }
}
