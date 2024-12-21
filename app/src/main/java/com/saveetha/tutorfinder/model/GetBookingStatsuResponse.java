package com.saveetha.tutorfinder.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetBookingStatsuResponse {
    private int status;
    private String current_status;
    @SerializedName("status_options")
    private ArrayList<String> status_options;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public ArrayList<String> getStatus_options() {
        return status_options;
    }

    public void setStatus_options(ArrayList<String> status_options) {
        this.status_options = status_options;
    }
}
