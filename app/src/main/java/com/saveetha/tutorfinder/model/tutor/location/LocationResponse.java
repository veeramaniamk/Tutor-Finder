package com.saveetha.tutorfinder.model.tutor.location;

import java.util.ArrayList;

public class LocationResponse {
    public int status;
    public String message;
    public ArrayList<LocationInnerClass> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LocationInnerClass> getData() {
        return data;
    }

    public void setData(ArrayList<LocationInnerClass> data) {
        this.data = data;
    }

}
